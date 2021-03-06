package com.sprint.ctrls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.common.EncodePassword;
import com.sprint.common.Result;
import com.sprint.models.domain.Account;
import com.sprint.models.domain.Admin;
import com.sprint.models.domain.AdminWithoutPwd;
import com.sprint.models.domain.LoginAdmin;
import com.sprint.models.domain.Member;
import com.sprint.models.domain.MemberWithoutPwd;
import com.sprint.services.AdminService;
import com.sprint.services.MemberService;
@RestController
public class AdminCtrl {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/admin/login", method=RequestMethod.POST)
	public Result login(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) {
		Result result = new Result();
		LoginAdmin loginAdmin = new LoginAdmin();
		AdminWithoutPwd admin = null;//??new 
		try {
				account.setAdminPassword(EncodePassword.encodePassword(account.getAdminPassword()));
				admin = adminService.findByAdminAccount(account);
				if(admin != null) {
					System.out.println("4");
					request.getSession().removeAttribute("member");
					request.getSession().setAttribute("admin", account.getAdminAccount());
					loginAdmin.setAdminName(admin.getAdminName());
					loginAdmin.setPremission(admin.getPremission());
					loginAdmin.setUrl("/manage");
					result.setResult(loginAdmin);
				} else {
					System.out.println("Admin 登录认证失败");
					result.setStatus(6, "账号密码不匹配");
				}
				
			//会员登陆
			Member m = new Member();
			m.setCardNumber(account.getAdminAccount());
			m.setMemberPassword(account.getAdminPassword());
			MemberWithoutPwd mem = memberService.findByAccount(m);
			if(mem != null){
				request.getSession().removeAttribute("admin");
				request.getSession().setAttribute("member", account.getAdminAccount());
				loginAdmin.setAdminName(mem.getMemberName());
				loginAdmin.setPremission(0);
				loginAdmin.setUrl("/membermodel");
				result.setResult(loginAdmin);
			} else {
				System.out.println("Member 登录认证失败");
				result.setStatus(6, "账号密码不匹配");
			}
				
		} catch(Exception e) {
			System.out.println("encode exception");
			e.printStackTrace();
		} finally {
		}
		return result;
	}
	@RequestMapping(value="/admin", method=RequestMethod.POST)
	public Result createAdmin(@RequestBody Admin admin) {
		Result result = new Result();
		try {
			if (adminService.findByAdminAccountNumber(admin.getAdminAccount()) == null) {
				admin.setAdminPassword(EncodePassword.encodePassword(admin.getAdminPassword()));
				adminService.createAdmin(admin);
				result.setResult(adminService.findByAdminAccountNumber(admin.getAdminAccount()));
			} else {
				result.setStatus(6, "该账户已经存在!");
			}
		} catch(NullPointerException e) {
			result.setStatus(0, "Action completed failed");
			System.out.println("nullPointerException");
			e.printStackTrace();
		} catch(Exception e) {
			result.setStatus(0, "Action completed failed");
			System.out.println("Catch Exception");
			e.printStackTrace();

		}
		return result;
	}

	@RequestMapping(value="/admin/{id}", method=RequestMethod.DELETE)
	public Result deleteAdmin(@PathVariable int id) {
		Result result = new Result();
		try {
			if(adminService.findByAdminId(id).getPremission() != 999) {
			adminService.deleteAdmin(id);
			if(adminService.findByAdminId(id) == null) {
				//admin.getAdminAccount()
				return result;
			} else {
				result.setStatus(7, "删除不成功!");
			}
			} else {
				result.setStatus(5, "没有删除权限！");
			}
		} catch(NullPointerException e){
			result.setStatus(0, "Action completed failed");
			System.out.println("nullPointerException");
			e.printStackTrace();
		} catch(Exception e) {
			result.setStatus(0, "Action completed failed");
			System.out.println("Catch Exception");
			e.printStackTrace();
		}

		return result;
	}
	
	@RequestMapping(value="/admin/{id}", method=RequestMethod.PUT)
	public Result updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
		Result result = new Result();
		try {
			System.out.println(id);
			admin.setId(id);
			if(admin.getId() != -1) {
				admin.setAdminPassword(EncodePassword.encodePassword(admin.getAdminPassword()));
				adminService.updateAdmin(admin);
				result.setResult(adminService.findByAdminId(id));	
			} else {
				result.setStatus(9, "修改失败！");
			}
		} catch(NullPointerException e) {
			result.setStatus(0, "Action completed failed");
			System.out.println("nullPointerException");
			e.printStackTrace();
		} catch(Exception e) {
			result.setStatus(0, "Action completed failed");
			System.out.println("Catch Exception");
			e.printStackTrace();
		}		
		return result;
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public Result findAdmin(String key) {
		Result result = new Result();
		if(key == null || key.equals("") == true) {
			result.setResult(adminService.findAdmin());
		} else {
			result.setResult(adminService.findAdminByKey(key));
		}	
		return result;
	}

	@RequestMapping(value="/admin/{adminAccount}", method=RequestMethod.GET)
	public Result findByAdminAccount(@PathVariable String adminAccount) {
		Result result = new Result();
		if (adminAccount != null && !adminAccount.equals(""))
			result.setResult(adminService.findByAdminAccountNumber(adminAccount));	
		return result;
	}

	@RequestMapping("quits")
	public String quit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println("ssss");
		return "login";
	} 
}
