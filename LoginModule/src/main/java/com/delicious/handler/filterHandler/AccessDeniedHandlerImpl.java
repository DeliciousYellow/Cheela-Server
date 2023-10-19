package com.delicious.handler.filterHandler;

import com.delicious.exception.PermissionDeniedException;
import com.delicious.pojo.ResultEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    /**
     * 权限不足时抛出的异常
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        request.setAttribute("PermissionDeniedException", new PermissionDeniedException(ResultEnum.TOKEN_ERROR));
        request.getRequestDispatcher("/loginAbout/error").forward(request, response);
    }
}
