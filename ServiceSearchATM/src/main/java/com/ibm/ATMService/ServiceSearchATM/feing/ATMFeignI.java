package com.ibm.ATMService.ServiceSearchATM.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url="${feign.client.atm.url}", name="${feign.client.atm.name}")
@RequestMapping("${feign.client.atm.mapping}")
public interface ATMFeignI {
	
	@GetMapping(value="${feign.client.atm.get}")
	public String getJsonCallBack();
}
