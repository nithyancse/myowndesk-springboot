package com.myowndesk.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.myowndesk.service.inter.IMessageByLocaleService;

@Component
public class MessageByLocaleService implements IMessageByLocaleService {

    @Autowired
    private MessageSource messageSource;
    
    public String getMessage(String id) {
    	Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id,null,locale);
	}

    public String getMessage(String id, Locale locale) {
        return messageSource.getMessage(id,null,locale);
    }
}

