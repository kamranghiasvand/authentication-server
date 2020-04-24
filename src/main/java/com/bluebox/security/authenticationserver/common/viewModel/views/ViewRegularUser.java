package com.bluebox.security.authenticationserver.common.viewModel.views;

/**
 * @author by kamran ghiasvand
 */
public class ViewRegularUser {
    public interface PublicRequest {
    }
    public interface CreateRequest extends PublicRequest {
    }
    public interface LoginRequest {
    }

    public interface UpdateRequest extends PublicRequest {
    }

    public interface Response extends PublicRequest, ViewMain.Query  {
    }
}
