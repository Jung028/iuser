//package com.
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.alipay.alipay_plus.biz.user.cache.OtpChallenge;
//import com.alipay.alipay_plus.biz.user.template.UserServiceTemplate;
//import com.alipay.alipay_plus.biz.user.user.impl.UserServiceImpl;
//import com.alipay.alipay_plus.common.service.facade.baseresult.UserBizResult;
//import com.alipay.alipay_plus.common.service.facade.item.OtpVerifiedClaims;
//import com.alipay.alipay_plus.common.service.facade.request.RegisterUserRequest;
//import com.alipay.alipay_plus.core.domain.UserInfo;
//import com.alipay.alipay_plus.core.service.repository.UserInfoRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.transaction.support.TransactionTemplate;
//
//public class RegisterUserProcessTest {
//
//    @Mock
//    private TransactionTemplate userTransactionTemplate;
//
//    @Mock
//    private UserServiceTemplate userServiceTemplate;
//
//    @InjectMocks
//    private UserServiceImpl userService; // userServiceTemplate will be injected here
//
//    @Mock
//    private OtpChallenge otpChallenge;
//
//    @Mock
//    private UserInfoRepository userInfoRepository;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRegister_successful() {
//        // Arrange
//        RegisterUserRequest request = new RegisterUserRequest();
//        request.setPhoneNo("1234567890");
//        request.setPassword("password123");
//        request.setVerifiedToken("validToken");
//
//        UserBizResult<Void> result = new UserBizResult<>();
//
//        // Mock dependencies
//        OtpVerifiedClaims claims = new OtpVerifiedClaims();
//        claims.setPhoneNo("1234567890");
//        when(otpChallenge.verifyVerifiedToken("validToken")).thenReturn(claims);
//
//        when(userInfoRepository.queryUserInfo("1234567890")).thenReturn(null);
//
//        // Act
//        UserBizResult<Void> response = userService.register(request);
//
//        // Assert
//        assertTrue(response.isSuccess());
//        assertEquals("Successfully created account", response.getResultMessage());
//        verify(userInfoRepository).insertUserInfo(any(UserInfo.class));
//    }
//}