package com.sprint.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.common.Result;
import com.sprint.models.dao.ConsumeRecordDao;
import com.sprint.models.domain.ConsumeRecord;
import com.sprint.models.domain.GoodsChangeCount;
import com.sprint.models.domain.Member;
import com.sprint.models.domain.MemberRank;
import com.sprint.models.domain.MemberWithoutPwd;
import com.sprint.models.domain.MoneyIn;
@Service
public class ConsumeRecordService {
	
	@Autowired
	private ConsumeRecordDao consumeRecordDao;
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRankService memberRankService;

	@Autowired
	private GoodsService goodsService;

	public void createConsumeRecord(ConsumeRecord consumeRecord) {
		//卡号--->会员等级--->折扣率
		Result result = new Result();

		MemberWithoutPwd member = memberService.findByCardNumber(consumeRecord.getCardNumber());
		double rate = memberRankService.findByMemberRank(memberService.findByCardNumber(consumeRecord.getCardNumber()).getMemberRank()).getDiscount();
		//商品号--->单价
		double price = goodsService.findByGoodsNumber(consumeRecord.getGoodsNumber()).getGoodsPrice();
		String goodsName = goodsService.findByGoodsNumber(consumeRecord.getGoodsNumber()).getGoodsName();
		//总价 * 折扣率  <= 卡号--->money
		double consumeMoney = rate * price * consumeRecord.getGoodsCount();
		consumeRecord.setMemberName(member.getMemberName());
		consumeRecord.setMemberRank(member.getMemberRank());
		consumeRecord.setGoodsPrice(price);
		consumeRecord.setGoodsName(goodsName);
		consumeRecord.setConsumeMoney(consumeMoney);
		System.out.println("zxc");
		consumeRecordDao.createConsumeRecord(consumeRecord);
		
		// 积分增加
		Member m = memberService.findByID(member.getId());
		m.setIntegral(m.getIntegral()+consumeMoney);
		// 等级升级
		List<MemberRank> ranks = memberRankService.findAll();
		Map<String,Double> map = new HashMap<>();
		for(MemberRank rank: ranks){
			map.put(rank.getMemberRank(), rank.getIntegral());
		}
		List<Map.Entry<String,Double>> list=new ArrayList<>();  
        list.addAll(map.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
        });
		
        for(Map.Entry<String,Double> mapping:list){
            if(m.getIntegral()>=mapping.getValue()){ 
            	//升级
            	m.setMemeberRank(mapping.getKey());
            	System.out.println("会员"+m.getCardNumber()+"("+m.getMemberName()+")已自动升级为"+mapping.getKey()+"当前积分："+m.getIntegral());
            	break;
            }
       }
        memberService.updateMember(m);
	}
	
	public void updateConsumeRecord(ConsumeRecord consumeRecord) {
		//卡号--->会员等级--->折扣率
		Result result = new Result();

		MemberWithoutPwd member = memberService.findByCardNumber(consumeRecord.getCardNumber());
		double rate = memberRankService.findByMemberRank(memberService.findByCardNumber(consumeRecord.getCardNumber()).getMemberRank()).getDiscount();
		//商品号--->单价
		double price = goodsService.findByGoodsNumber(consumeRecord.getGoodsNumber()).getGoodsPrice();
		String goodsName = goodsService.findByGoodsNumber(consumeRecord.getGoodsNumber()).getGoodsName();
		//总价 * 折扣率  <= 卡号--->money
		double consumeMoney = rate * price * consumeRecord.getGoodsCount();
		consumeRecord.setMemberName(member.getMemberName());
		consumeRecord.setMemberRank(member.getMemberRank());
		consumeRecord.setGoodsPrice(price);
		consumeRecord.setGoodsName(goodsName);
		consumeRecord.setConsumeMoney(consumeMoney);
		consumeRecordDao.updateConsumeRecord(consumeRecord);
		
	}
	public ConsumeRecord findByOrderNumber(String orderNumber) {
		return consumeRecordDao.findByOrderNumber(orderNumber);
	}

	public void deleteConsumeRecord(int id) {
		consumeRecordDao.deleteConsumeRecord(id);
	}

	public	List<ConsumeRecord> findByCardNumber(String cardNumber) {
		return consumeRecordDao.findByCardNumber(cardNumber);
	}
	
	public List<ConsumeRecord> findByCardNumber1(String cardNumber) {
		return consumeRecordDao.findByCardNumber1(cardNumber);
	}

	public List<ConsumeRecord> findAll() {
		return consumeRecordDao.findAll();
	}
	
	public List<ConsumeRecord> findAll1() {
		return consumeRecordDao.findAll1();
	}

	public void updateStatus(ConsumeRecord cr) {
		consumeRecordDao.updateStatus(cr);
	}
	public void orderSettlement(String orderNumber) {
		
		//orderNumber --->member ---> cardNumber
		//orderNumber --->cousmeMoney    减掉钱数
		ConsumeRecord consumeRecord = findByOrderNumber(orderNumber);
		MoneyIn moneyOut = new MoneyIn();
		moneyOut.setCardNumber(consumeRecord.getCardNumber());
		moneyOut.setMoney(consumeRecord.getConsumeMoney());
		System.out.println(consumeRecord.getConsumeMoney());
		memberService.updateMoneyOut(moneyOut);

		//orderNumber ---> count & goodsNumber  减掉数据库中的数量
		GoodsChangeCount gcc = new GoodsChangeCount();
		gcc.setGoodsNumber(consumeRecord.getGoodsNumber());
		gcc.setGoodsCount(consumeRecord.getGoodsCount());
		goodsService.updateGoodsCount(gcc);
		//修改status
		consumeRecord.setStatus(1);
		updateStatus(consumeRecord);
	}

}
