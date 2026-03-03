import com.alipay.usercenter.common.service.facade.api.UserService;
import com.alipay.usercenter.common.service.facade.baseresult.UserBizResult;
import com.alipay.usercenter.common.service.facade.request.LoginRequest;

/**
 * @author adam
 * @date 3/3/2026 7:43 PM
 */
public class UserServiceTest {
    public UserService userService;

    public UserBizResult<String> login(LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}