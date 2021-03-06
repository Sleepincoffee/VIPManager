package com.sprint.ctrls;


import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import com.sprint.common.EncodePassword;
import com.sprint.common.Result;
import com.sprint.models.domain.Member;
import com.sprint.models.domain.MemberUpdatePwd;
import com.sprint.models.domain.MemberUpdateStatus;
import com.sprint.services.MemberService;
import com.sprint.util.SendEmail;
import com.sprint.valids.MemberValid;
@RestController
//@SessionAttributes(value="code")
public class MemberCtrl {
	
	@Autowired
	private MemberService memberService;
    
	@Autowired
	private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    
	@RequestMapping(value="/member", method=RequestMethod.POST)
	public Result createMember(@Valid @RequestBody Member member,HttpSession session) {
		Result result = new Result();
		try {
			if (MemberValid.memberValid(member)) {
				member.setMemberPassword(EncodePassword.encodePassword(member.getMemberPassword()));
				if (memberService.findByCardNumber(member.getCardNumber()) == null) {
					memberService.createMember(member);
					result.setResult(memberService.findByCardNumber(member.getCardNumber()));
				} else {
					result.setStatus(6, "卡号已存在");
				}
			} else {
				result.setStatus(7, "请求参数非法！");
			}
			//发送邮件
			///邮件的内容
			String email = member.getEmail();
			if(!email.equals("")&&email.contains("@")){
				String[] s = {"2f4d","6hdg","s8jh"};
				String code = s[new Random().nextInt(3)];
//				ApplicationContext
		        StringBuffer sb=new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");  
		        sb.append("<a href=\"http://localhost:9999/member/active?&email=");  
		        sb.append(email);   
		        sb.append("&originCode=");   
		        sb.append("origin"+code);
		        sb.append("&cardNumber=");   
		        sb.append(member.getCardNumber());
		        sb.append("&validateCode=");   
		        sb.append(code);
		        sb.append("\">"+member.getMemberName()+"("+member.getCardNumber()+")邮箱验证:");   
		        sb.append(code);
		        sb.append("</a>");  
		          
		        //发送邮件  
		        new SendEmail().send(email, sb.toString());
		        System.out.println("发送邮件"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("encode exception");
		}	
		return result;
	}

	@RequestMapping(value="/member/{id}", method=RequestMethod.DELETE)
	public Result deleteMember(@PathVariable int id) {
		Result result = new Result();
		memberService.deleteMember(id);
		return result;
	}
	
	@RequestMapping(value="/member/{id}", method=RequestMethod.PUT)
	public Result updateMember(@RequestBody Member member) {
		Result result = new Result();
		memberService.updateMember(member);
		try {
			if (MemberValid.memberValid(member)) {
				memberService.updateMember(member);
				result.setResult(memberService.findByCardNumber(member.getCardNumber()));
			} else {
				result.setStatus(6, "请求参数非法!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("encode exception");
		}	
		return result;
	}

	@RequestMapping(value="/member", method=RequestMethod.GET)
	public Result findMember(String key, HttpServletRequest request) {
	
		Result result = new Result();
		//会员登录所有的操作都值能访问自己的 输入自己的卡号 不能越权
		String sessioncardnumber = (String)request.getSession().getAttribute("member");
		if(sessioncardnumber!=null && sessioncardnumber.equals("") == false){
			if(key==null || key.equals(sessioncardnumber) == false){
				result.setStatus(1, "非管理员仅可输入自己卡号！");
				return result;
			}
		}
		
		if(key != null && key.equals("") == false) {
			result.setResult(memberService.findByKey(key));
		} else {
			result.setResult(memberService.findAll());
		}
		return result;
	}

	@RequestMapping(value="/member/updateStatus", method=RequestMethod.PUT) 
	public Result updateStatus(@RequestBody MemberUpdateStatus memberUpdateStatus) {
		Result result = new Result();
		System.out.println("请求过来-----");
		if (memberService.findByCardNumber(memberUpdateStatus.getCardNumber()) != null) {
			memberService.updateStatus(memberUpdateStatus);			
		} else {
			result.setStatus(5, "卡号不存在");
		}
		return result;
	}
	

	@RequestMapping(value="/member/active", method=RequestMethod.PUT)
	public Result updatePassword(@RequestBody MemberUpdatePwd memberUpdatePwd) {
		Result result = new Result();
		Member member = new Member();
		member.setCardNumber(memberUpdatePwd.getCardNumber());
		try {
			member.setMemberPassword(EncodePassword.encodePassword(memberUpdatePwd.getOldMemberPassword()));
		
			if (memberService.findByAccount(member) != null) {
				try {
					memberUpdatePwd.setNewMemberPassword(EncodePassword.encodePassword(memberUpdatePwd.getNewMemberPassword()));
					memberService.updatePassword(memberUpdatePwd);
				} catch (Exception e) {
					System.out.println("encode exception");
					e.printStackTrace();
				}
			} else {
				result.setStatus(5, "原始密码错误!");
			}
		} catch (Exception e) {
			System.out.println("encode exception");
			e.printStackTrace();
			
		}
		return result;
	}
	
	@RequestMapping(value="/member/active", method=RequestMethod.GET)
	public String active(@RequestParam("email") String email, 
						@RequestParam("originCode") String originCode,
						@RequestParam("validateCode") String validateCode,
						@RequestParam("cardNumber") String cardNumber,
						HttpSession session){
//		String url = "http://127.0.0.1:9999/login";
		String url = "http://39.106.172.109:8080/login";
		Result result = new Result();
		originCode = originCode.replace("origin", "");
		if(originCode.equals(validateCode)){
			return "<script>alert('恭喜你"+cardNumber+",邮箱验证成功，页面将自动跳转至登陆页面！');window.location.href='"+url+"'</script>";
		}else{
			return "<script>alert('验证失败，请重新验证');window.location.href='"+url+"'</script>";
		}
	}
}
