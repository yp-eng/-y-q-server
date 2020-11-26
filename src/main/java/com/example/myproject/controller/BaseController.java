package com.example.myproject.controller;

import com.example.myproject.common.AppCommonConst;
import com.example.myproject.common.Result;
import com.example.myproject.common.ResultCode;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共Controller
 * @author HUANG
 * @create 2017/01/13 16:23
 */
@SuppressWarnings("all")
public class BaseController {

	protected Logger logger = null;

	public BaseController() {
		logger = Logger.getLogger(this.getClass());
	}


	/**
	 * 提示操作成功
	 * 
	 * @param data
	 * @return
	 */
	public Result returnSuccess(Object... data) {
		Result result = null;
		if (data != null && data.length > 0) {
			result = new Result(data[0]);
		} else {
			result = new Result();
		}
		return result;
	}

	/**
	 * 提示操作成功
	 * 
	 * @param data
	 * @return
	 */
	public Result returnSuccess(boolean flag, String errorMsg) {
		Result result = new Result();
		result.setData(flag);
		if (flag) {
			result.setMsg(errorMsg);
		}
		return result;
	}

	/**
	 * 提示系统异常
	 *
	 * @param ex
	 * @return
	 */
	public Result returnErrorMessag(Exception ex, String... errorMsg) {
		logger.debug(ex.getMessage(), ex);
		Result result = null;
		if (errorMsg != null && errorMsg.length > 0) {
			result = new Result(false, ResultCode.ERROR, errorMsg[0]);
		} else {
			result = new Result(false, ResultCode.ERROR, "系统异常");
		}
		return result;
	}

	/**
	 * 提示没有权限
	 *
	 * @return
	 */
	public Result returnNoPermission() {
		Result result = new Result(false, ResultCode.NO_PERMISSION, "未经授权的访问");
		return result;
	}

	/**
	 * 提示成功or失败
	 *
	 * @param number
	 * @return
	 */
	public Result returnSuccessOrFailByNumber(Integer number, String... errorMsg) {
		Result result = new Result();
		if (number == null || number <= 0) {
			result.setCode(ResultCode.FAILURE);
			result.setSuccess(false);
			if (errorMsg != null && errorMsg.length > 0) {
				result.setMsg(errorMsg[0]);
			} else {
				result.setMsg("操作失败");
			}
		} else if (number > 0) {
			result.setCode(ResultCode.SUCCESS);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 提示成功or失败
	 *
	 * @param number
	 * @return
	 */
	public Result returnSuccessOrFailByNumber(Integer number, Object data, String... errorMsg) {
		Result result = new Result();
		if (number == null || number <= 0) {
			result.setCode(ResultCode.FAILURE);
			result.setSuccess(false);
			if (errorMsg != null && errorMsg.length > 0) {
				result.setMsg(errorMsg[0]);
			} else {
				result.setMsg("操作失败");
			}
		} else if (number > 0) {
			result.setCode(ResultCode.SUCCESS);
			result.setSuccess(true);
		}
		result.setData(data);
		return result;
	}

	/**
	 * 提示操作失败
	 *
	 * @return
	 */
	public Result returnFail(String errorMsg, Object... data) {
		Result result = null;
		if (data != null && data.length > 0) {
			result = new Result(data[0]);
		} else {
			result = new Result();
		}
		result.setMsg(errorMsg);
		result.setSuccess(false);
		result.setCode(ResultCode.FAILURE);
		return result;
	}
	/**
	 * 信息提示
	 *
	 * @param msg
	 * @return
	 */
	public Map returnWarnMsg(String msg) {
		Map messge = new HashMap();
		messge.put(AppCommonConst.MESSGE_KEY, msg);
		return messge;
	}


	/**
	 * 提示成功or失败
	 *
	 * @param number
	 * @return
	 */
	public Map returnSuccessOrFail(Integer number) {
		Map messge = new HashMap();
		if (number == null) {
			messge.put(AppCommonConst.MESSGE_KEY, 0);
		} else if (number > 0) {
			messge.put(AppCommonConst.MESSGE_KEY, 1);
		} else {
			messge.put(AppCommonConst.MESSGE_KEY, 0);
		}
		return messge;
	}
}
