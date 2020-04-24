package com.bluebox.security.authenticationserver.common.viewModel.views;

/**
 * @author by kamran ghiasvand
 */
public class ViewPermission {
    public interface PublicRequest {
    }
    public interface CreateRequest extends PublicRequest {
    }


    public interface UpdateRequest extends PublicRequest {
    }

    public interface Response extends PublicRequest, ViewMain.Query  {
    }
}
