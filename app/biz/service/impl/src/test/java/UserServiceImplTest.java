//import com.alipay.account_center.common.service.facade.api.AccountService;
//import com.alipay.account_center.common.service.facade.baseresult.AccountBizResult;
//import com.alipay.account_center.common.service.facade.item.AccountInfoItem;
//import com.alipay.usercenter.biz.cache.OtpChallenge;
//import com.alipay.usercenter.biz.cache.UserSecurityCache;
//import com.alipay.usercenter.biz.jwt.JwtClaims;
//import com.alipay.usercenter.biz.jwt.JwtContextHolder;
//import com.alipay.usercenter.biz.jwt.JwtUtil;
//import com.alipay.usercenter.biz.template.UserBizCallback;
//import com.alipay.usercenter.biz.template.UserServiceTemplate;
//import com.alipay.usercenter.biz.user.impl.UserServiceImpl;
//import com.alipay.usercenter.biz.util.UserPasswordUtil;
//import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
//import com.alipay.usercenter.common.service.facade.enums.OTPSceneEnum;
//import com.alipay.usercenter.common.service.facade.item.OtpChallengeItem;
//import com.alipay.usercenter.common.service.facade.item.OtpVerifiedClaims;
//import com.alipay.usercenter.common.service.facade.item.UserInfoItem;
//import com.alipay.usercenter.common.service.facade.request.*;
//import com.alipay.usercenter.common.service.facade.result.LoginResult;
//import com.alipay.usercenter.common.service.facade.result.OTPResult;
//import com.alipay.usercenter.common.service.integration.account.AccountServiceClient;
//import com.alipay.usercenter.core.enums.UserAccountStatusEnum;
//import com.alipay.usercenter.core.enums.UserSecurityStatusEnum;
//import com.alipay.usercenter.core.exception.BaseSlipException;
//import com.alipay.usercenter.core.model.UserInfo;
//import com.alipay.usercenter.core.model.UserSecurity;
//import com.alipay.usercenter.core.service.repository.UserInfoRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.time.Instant;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
///**
// * Comprehensive test cases for UserServiceImpl
// * Tests cover login, OTP verification, user registration, and user info queries
// */
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserServiceTemplate userServiceTemplate;
//
//    @Mock
//    private UserInfoRepository userInfoRepository;
//
//    @Mock
//    private UserSecurityCache userSecurityCache;
//
//    @Mock
//    private AccountServiceClient accountServiceClient;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private OtpChallenge otpChallenge;
//
//    @Mock
//    private TransactionTemplate userTransactionTemplate;
//
//    private LoginRequest loginRequest;
//    private OTPRequest otpRequest;
//    private VerifyOtpRequest verifyOtpRequest;
//    private RegisterUserRequest registerRequest;
//    private QueryUserInfoRequest queryUserInfoRequest;
//
//    @BeforeEach
//    void setUp() {
//        // Setup login request
//        loginRequest = new LoginRequest();
//        loginRequest.setPhoneNo("0123456789");
//        loginRequest.setPassword("plainPassword");
//
//        // Setup OTP request
//        otpRequest = new OTPRequest();
//        otpRequest.setPhoneNo("012345678922");
//        otpRequest.setOtpScene(OTPSceneEnum.REGISTER);
//
//        // Setup verify OTP request
//        verifyOtpRequest = new VerifyOtpRequest();
//        verifyOtpRequest.setChallengeId("challenge-123");
//        verifyOtpRequest.setOtp("123456");
//        verifyOtpRequest.setSceneCode(OTPSceneEnum.REGISTER);
//
//        // Setup register request
//        registerRequest = new RegisterUserRequest();
//        registerRequest.setPhoneNo("0123456789221");
//        registerRequest.setPassword("newPasword123$");
//        registerRequest.setConfirmPassword("newPasword123$");
//        registerRequest.setVerifiedToken("verified-token-123");
//
//        // Setup query user info request
//        queryUserInfoRequest = new QueryUserInfoRequest();
//        queryUserInfoRequest.setPhoneNo("0123456789");
//        queryUserInfoRequest.setUserId("1");
//
//        // Mock template to execute callback methods directly
//        doAnswer(invocation -> {
//            Object req = invocation.getArgument(0);
//            UserBizCallback<?, ?> callback = invocation.getArgument(2);
//
//            Object response = invokeCallback(callback, "createDefaultResponse");
//            invokeCallback(callback, "checkParams", req);
//            invokeCallback(callback, "process", req, response);
//            return response;
//        }).when(userServiceTemplate).execute(any(), any(), any());
//    }
//
//    private static Object invokeCallback(UserBizCallback<?, ?> callback, String methodName, Object... args) throws Throwable {
//        java.lang.reflect.Method method = java.util.Arrays.stream(callback.getClass().getDeclaredMethods())
//                .filter(m -> m.getName().equals(methodName) && m.getParameterCount() == args.length)
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("Callback method not found: " + methodName));
//        method.setAccessible(true);
//        try {
//            return method.invoke(callback, args);
//        } catch (java.lang.reflect.InvocationTargetException e) {
//            throw e.getCause();
//        }
//    }
//
//    // ===============================
//    // LOGIN TESTS
//    // ===============================
//    @Test
//    void login_success() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(1L);
//        userInfo.setHashedPassword(UserPasswordUtil.hashPassword("plainPassword"));
//
//        UserSecurity security = new UserSecurity();
//        security.setUserId(1L);
//        security.setFailedAttempts(0);
//        security.setLockedUntil(Instant.now().minusSeconds(60));
//        security.setStatus("ACTIVE");
//
//        AccountBizResult<AccountInfoItem> accountBizResult = new AccountBizResult<>();
//        accountBizResult.setResult(new AccountInfoItem());
//        accountBizResult.setSuccess(true);
//
//        doReturn(userInfo).when(userInfoRepository).queryUserInfo("0123456789");
//        doReturn(security).when(userSecurityCache).queryUserSecurity(any());
//        doReturn("mocked-jwt").when(jwtUtil).generateTokenForUserInfo(any());
//        doReturn(accountBizResult).when(accountServiceClient).queryAccountInfoByUserId(any());
//
//        UserBizResult<LoginResult> result = userService.login(loginRequest);
//
//        assertTrue(result.isSuccess());
//        assertEquals("mocked-jwt", result.getResult().getJwtToken());
//        verify(userSecurityCache).delete(1L);
//        verify(jwtUtil).generateTokenForUserInfo(userInfo);
//    }
//
//    @Test
//    void login_locked_timeout() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(1L);
//        userInfo.setHashedPassword("hashed");
//
//        UserSecurity security = new UserSecurity();
//        security.setUserId(1L);
//        security.setFailedAttempts(3);
//        security.setLockedUntil(Instant.now().plusSeconds(300));
//        security.setStatus(UserSecurityStatusEnum.TIMEOUT_LOCK.getCode());
//
//        doReturn(userInfo).when(userInfoRepository).queryUserInfo(anyString());
//        doReturn(security).when(userSecurityCache).queryUserSecurity(any());
//
//        UserBizResult<LoginResult> result = userService.login(loginRequest);
//
//        assertFalse(result.isSuccess());
//        verify(jwtUtil, never()).generateTokenForUserInfo(any());
//        verify(userSecurityCache, never()).delete(any());
//    }
//
//    @Test
//    void login_incorrect_password_increment_attempt() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(1L);
//        userInfo.setHashedPassword(UserPasswordUtil.hashPassword("correctPassword"));
//
//        UserSecurity security = new UserSecurity();
//        security.setUserId(1L);
//        security.setFailedAttempts(1);
//        security.setLockedUntil(Instant.now().minusSeconds(60));
//        security.setStatus("ACTIVE");
//
//        doReturn(userInfo).when(userInfoRepository).queryUserInfo(anyString());
//        doReturn(security).when(userSecurityCache).queryUserSecurity(any());
//        doAnswer(invocation -> invocation.getArgument(0)).when(userSecurityCache).update(any());
//
//        UserBizResult<LoginResult> result = userService.login(loginRequest);
//
//        assertFalse(result.isSuccess());
//        verify(userSecurityCache).update(any());
//        verify(jwtUtil, never()).generateTokenForUserInfo(any());
//    }
//
//    @Test
//    void login_permanent_lock() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(1L);
//        userInfo.setHashedPassword(UserPasswordUtil.hashPassword("wrongPassword"));
//
//        UserSecurity security = new UserSecurity();
//        security.setUserId(1L);
//        security.setFailedAttempts(10);
//        security.setLockedUntil(Instant.now().minusSeconds(60));
//        security.setStatus(UserSecurityStatusEnum.PERMANENT_LOCK.name());
//
//        doReturn(userInfo).when(userInfoRepository).queryUserInfo(anyString());
//        doReturn(security).when(userSecurityCache).queryUserSecurity(any());
//        doReturn(security).when(userSecurityCache).update(any());
//
//        UserBizResult<LoginResult> result = userService.login(loginRequest);
//
//        assertFalse(result.isSuccess());
//        verify(userSecurityCache).update(any());
//        verify(jwtUtil, never()).generateTokenForUserInfo(any());
//    }
//
//    @Test
//    void login_user_not_found() {
//
//        doReturn(null).when(userInfoRepository).queryUserInfo(any());
//
//        // Should throw an exception when user not found
//        assertThrows(Exception.class, () -> userService.login(loginRequest));
//    }
//
//    // ===============================
//    // SEND OTP TESTS
//    // ===============================
//    @Test
//    void sendOTP_success() {
//        OtpChallengeItem challenge = new OtpChallengeItem();
//        challenge.setChallengeId("challenge-123");
//        challenge.setSceneCode(OTPSceneEnum.REGISTER);
//        challenge.setExpireAt(Instant.now().plusSeconds(300));
//        challenge.setMaxRetry(3);
//        challenge.setRetryCount(0);
//
//        OTPResult otpResult = new OTPResult();
//        otpResult.setChallengeId("challenge-123");
//        otpResult.setSceneCode(OTPSceneEnum.REGISTER);
//        otpResult.setRetryLeft(3);
//
//        UserBizResult<OTPResult> expectedResult = new UserBizResult<>();
//        expectedResult.setSuccess(true);
//        expectedResult.setResult(otpResult);
//
//        doReturn(challenge).when(otpChallenge).createAndStoreChallenge(anyString(), any());
//
//        UserBizResult<OTPResult> result = userService.sendOTP(otpRequest);
//
//        assertTrue(result.isSuccess());
//        assertNotNull(result.getResult());
//        assertEquals("challenge-123", result.getResult().getChallengeId());
//        assertEquals(OTPSceneEnum.REGISTER, result.getResult().getSceneCode());
//        assertEquals(3, result.getResult().getRetryLeft());
//
//        verify(otpChallenge).createAndStoreChallenge(otpRequest.getPhoneNo(), otpRequest.getOtpScene());
//    }
//
//    @Test
//    void sendOTP_with_existing_challenge() {
//        OtpChallengeItem challenge = new OtpChallengeItem();
//        challenge.setChallengeId("challenge-456");
//        challenge.setSceneCode(OTPSceneEnum.REGISTER);
//        challenge.setExpireAt(Instant.now().plusSeconds(600));
//        challenge.setMaxRetry(5);
//        challenge.setRetryCount(1);
//
//        OTPResult otpResult = new OTPResult();
//        otpResult.setChallengeId("challenge-456");
//        otpResult.setSceneCode(OTPSceneEnum.REGISTER);
//        otpResult.setRetryLeft(4);
//
//        UserBizResult<OTPResult> expectedResult = new UserBizResult<>();
//        expectedResult.setSuccess(true);
//        expectedResult.setResult(otpResult);
//
//        doReturn(challenge).when(otpChallenge).createAndStoreChallenge(anyString(), any());
//
//        UserBizResult<OTPResult> result = userService.sendOTP(otpRequest);
//
//        assertTrue(result.isSuccess());
//        assertEquals("challenge-456", result.getResult().getChallengeId());
//        assertEquals(4, result.getResult().getRetryLeft());
//    }
//
//    // ===============================
//    // VERIFY OTP TESTS
//    // ===============================
//    @Test
//    void verifyOTP_success() {
//        String correctOtp = "123456";
//        String hashedOtp = BCrypt.hashpw(correctOtp, BCrypt.gensalt());
//
//        OtpChallengeItem challenge = new OtpChallengeItem();
//        challenge.setChallengeId("challenge-123");
//        challenge.setSceneCode(OTPSceneEnum.REGISTER);
//        challenge.setOtpHash(hashedOtp);
//        challenge.setLockoutUntil(Instant.now().minusSeconds(60));
//        challenge.setRetryCount(0);
//        challenge.setMaxRetry(3);
//        challenge.setVerified(false);
//
//        UserBizResult<String> expectedResult = new UserBizResult<>();
//        expectedResult.setSuccess(true);
//        expectedResult.setResult("verified-token-123");
//
//        doReturn(challenge).when(otpChallenge).queryOTP(anyString());
//        doReturn("verified-token-123").when(otpChallenge).issueJWTToken(any());
//        doNothing().when(otpChallenge).update(any());
//
//        UserBizResult<String> result = userService.verifyOTP(verifyOtpRequest);
//
//        assertTrue(result.isSuccess());
//        assertEquals("verified-token-123", result.getResult());
//        verify(otpChallenge).update(any());
//    }
//
//    @Test
//    void verifyOTP_invalid_otp() {
//        String correctOtp = "123456";
//        String hashedOtp = BCrypt.hashpw(correctOtp, BCrypt.gensalt());
//
//        OtpChallengeItem challenge = new OtpChallengeItem();
//        challenge.setChallengeId("challenge-123");
//        challenge.setSceneCode(OTPSceneEnum.REGISTER);
//        challenge.setOtpHash(hashedOtp);
//        challenge.setLockoutUntil(Instant.now().minusSeconds(60));
//        challenge.setRetryCount(0);
//        challenge.setMaxRetry(3);
//        challenge.setVerified(false);
//
//        UserBizResult<String> expectedResult = new UserBizResult<>();
//        expectedResult.setSuccess(false);
//
//        doReturn(challenge).when(otpChallenge).queryOTP(anyString());
//        doNothing().when(otpChallenge).update(any());
//
//        VerifyOtpRequest wrongOtpRequest = new VerifyOtpRequest();
//        wrongOtpRequest.setChallengeId("challenge-123");
//        wrongOtpRequest.setOtp("wrongOtp");
//        wrongOtpRequest.setSceneCode(OTPSceneEnum.REGISTER);
//        assertThrows(Exception.class, () -> userService.verifyOTP(wrongOtpRequest));
//        verify(otpChallenge).update(any());
//    }
//
//    @Test
//    void verifyOTP_max_retries_exceeded() {
//        String correctOtp = "123456";
//        String hashedOtp = BCrypt.hashpw(correctOtp, BCrypt.gensalt());
//
//        OtpChallengeItem challenge = new OtpChallengeItem();
//        challenge.setChallengeId("challenge-123");
//        challenge.setSceneCode(OTPSceneEnum.REGISTER);
//        challenge.setOtpHash(hashedOtp);
//        challenge.setLockoutUntil(Instant.now().plusSeconds(300));
//        challenge.setRetryCount(3);
//        challenge.setMaxRetry(3);
//        challenge.setVerified(false);
//
//        doReturn(challenge).when(otpChallenge).queryOTP(anyString());
//
//        VerifyOtpRequest wrongOtpRequest = new VerifyOtpRequest();
//        wrongOtpRequest.setChallengeId("challenge-123");
//        wrongOtpRequest.setOtp("wrongOtp");
//        wrongOtpRequest.setSceneCode(OTPSceneEnum.REGISTER);
//
//        // Should throw timeout lock exception
//        assertThrows(Exception.class, () -> userService.verifyOTP(wrongOtpRequest));
//    }
//
//    @Test
//    void verifyOTP_scene_code_mismatch() {
//        String correctOtp = "123456";
//        String hashedOtp = BCrypt.hashpw(correctOtp, BCrypt.gensalt());
//
//        OtpChallengeItem challenge = new OtpChallengeItem();
//        challenge.setChallengeId("challenge-123");
//        challenge.setSceneCode(OTPSceneEnum.REGISTER);
//        challenge.setOtpHash(hashedOtp);
//        challenge.setLockoutUntil(Instant.now().minusSeconds(60));
//        challenge.setRetryCount(0);
//        challenge.setMaxRetry(3);
//        challenge.setVerified(false);
//
//        doReturn(challenge).when(otpChallenge).queryOTP(anyString());
//
//        VerifyOtpRequest wrongSceneRequest = new VerifyOtpRequest();
//        wrongSceneRequest.setChallengeId("challenge-123");
//        wrongSceneRequest.setOtp(correctOtp);
//        wrongSceneRequest.setSceneCode(OTPSceneEnum.RESET_PASSWORD);
//
//        // Should throw scene mismatch exception
//        assertThrows(Exception.class, () -> userService.verifyOTP(wrongSceneRequest));
//    }
//
//    // ===============================
//    // REGISTER USER TESTS
//    // ===============================
//    @Test
//    void register_success() {
//        OtpVerifiedClaims claims = new OtpVerifiedClaims();
//        claims.setPhoneNo("0123456789221");
//
//        UserBizResult<Void> expectedResult = new UserBizResult<>();
//        expectedResult.setSuccess(true);
//        AccountBizResult<String> accountBizResult = new AccountBizResult<>();
//        accountBizResult.setSuccess(true);
//
//
//        doReturn(claims).when(otpChallenge).verifyVerifiedToken(anyString());
//        doReturn(null).when(userInfoRepository).queryUserInfo(anyString());
//        doAnswer(invocation -> {
//                    var callback = invocation.getArgument(0);
//                    return ((org.springframework.transaction.support.TransactionCallback<?>) callback).doInTransaction(null);
//                }).when(userTransactionTemplate).execute(any());
//        doReturn(accountBizResult).when(accountServiceClient).createAccount(any());
//
//        UserBizResult<Void> result = userService.register(registerRequest);
//
//        assertTrue(result.isSuccess());
//        verify(otpChallenge).verifyVerifiedToken(registerRequest.getVerifiedToken());
//    }
//
//    @Test
//    void register_phone_number_mismatch() {
//        OtpVerifiedClaims claims = new OtpVerifiedClaims();
//        claims.setPhoneNo("9999999999");
//
//        UserInfo newUserInfo = new UserInfo();
//        newUserInfo.setPhoneNo("0123456789");
//
//        doReturn(claims).when(otpChallenge).verifyVerifiedToken(anyString());
//        doAnswer(invocation -> {
//                    var callback = invocation.getArgument(0);
//                    return ((org.springframework.transaction.support.TransactionCallback<?>) callback).doInTransaction(null);
//                }).when(userTransactionTemplate).execute(any());
//
//        // Should throw phone mismatch exception
//        assertThrows(Exception.class, () -> userService.register(registerRequest));
//    }
//
//
//    // ===============================
//    // QUERY USER INFO TESTS
//    // ===============================
//    @Test
//    void queryUserInfo_success() {
//        JwtClaims claims = new JwtClaims();
//        claims.setSubject("1");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setStatus(UserAccountStatusEnum.ACTIVE.getCode());
//
//        UserInfoItem userInfoItem = new UserInfoItem();
//        UserBizResult<UserInfoItem> expectedResult = new UserBizResult<>();
//        expectedResult.setSuccess(true);
//        expectedResult.setResult(userInfoItem);
//
//        try (var mockedContextHolder = mockStatic(JwtContextHolder.class)) {
//            mockedContextHolder.when(JwtContextHolder::get).thenReturn(claims);
//
//            doReturn(userInfo).when(userInfoRepository).queryUserInfo(any());
//
//            UserBizResult<UserInfoItem> result = userService.queryUserInfo(queryUserInfoRequest);
//
//            assertNotNull(result);
//            assertNotNull(result.getResult());
//            verify(userInfoRepository).queryUserInfo(anyString());
//        }
//    }
//
//    @Test
//    void queryUserInfo_no_permission() {
//        JwtClaims claims = new JwtClaims();
//        claims.setUserId("999"); // Different user
//        try (var mockedContextHolder = mockStatic(JwtContextHolder.class)) {
//            mockedContextHolder.when(JwtContextHolder::get).thenReturn(claims);
//            // Should throw no permission exception
//            assertThrows(Exception.class, () -> userService.queryUserInfo(queryUserInfoRequest));
//        }
//    }
//
//    @Test
//    void queryUserInfo_user_not_found() {
//        JwtClaims claims = new JwtClaims();
//        claims.setSubject("1");
//
//        try (var mockedContextHolder = mockStatic(JwtContextHolder.class)) {
//            mockedContextHolder.when(JwtContextHolder::get).thenReturn(claims);
//
//            // Mock repository to return null when user is not found
//            doReturn(null).when(userInfoRepository).queryUserInfo(any());
//            UserBizResult<UserInfoItem> result = userService.queryUserInfo(queryUserInfoRequest);
//            // Should throw BaseSlipException when user not found
//            assertTrue(result.getResult() == null);
//        }
//    }
//}
//
