package com.bluebox.planner.auth.common.dto.views;

/**
 * @author by kamran ghiasvand
 */
public class ViewUser {
    public interface PublicRequest {
    }
    public interface CreateRequest extends PublicRequest {
    }

    public interface UpdateRequest extends PublicRequest {
    }

    public interface Response extends PublicRequest, ViewMain.Query  {
    }
}
