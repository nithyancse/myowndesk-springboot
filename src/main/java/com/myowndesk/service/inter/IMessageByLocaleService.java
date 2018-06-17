package com.myowndesk.service.inter;

import java.util.Locale;

public interface IMessageByLocaleService {
	
	public String getMessage(String id);

	public String getMessage(String id, Locale locale);
	
}
