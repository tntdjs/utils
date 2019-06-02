package com.tntdjs.utils.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.tntdjs.utils.SystemPropertyMgr;

/**
 * Translation Manager handles all internationalization 
 * of text in the application and output.
 * @author tsenausk
 *
 */
public class TranslationMgr {
	private static final Logger LOGGER = LogManager.getLogger(TranslationMgr.class.getName());
	/** Instance of Translation Manager */
	private static TranslationMgr instance;
	/** Translated messages */
	private ResourceBundle i18nMessages;
	
	/**
	 * constructor
	 */
	public TranslationMgr() {
		final Locale currentLocale = new Locale(SystemPropertyMgr.getInstance().getString("locale.language"), SystemPropertyMgr.getInstance().getString("locale.country"));
		i18nMessages = ResourceBundle.getBundle("i18n/translations", currentLocale);
	}
	
	/**
	 * getText method
	 * @param key
	 * @return
	 */
	public String getText(String key) {
		Enumeration<String> enums = i18nMessages.getKeys();
		while (enums.hasMoreElements()) {
			if (key.equalsIgnoreCase(enums.nextElement())) {
				return i18nMessages.getString(key);
			}
		}
		return key;
	}
	
	/**
	 * getInstance
	 * @return
	 */
	public synchronized static TranslationMgr getInstance() {
		if (null == instance) {
			instance = new TranslationMgr();
		}
		return instance;
	}

	/**
	 * getAppTitle
	 * @return
	 */
	public String getAppTitle() {
		return TranslationMgr.getInstance().getText(SystemPropertyMgr.getInstance().getString("app.title"));
	}
	
	/**
	 * TEST Translations
	 * requires Utils project to have the soloplayer/res 
	 * folder as a source resource in project properties
	 * @param args
	 */
	public static void main(final String[] args) {
		LOGGER.info(TranslationMgr.getInstance().getText("this"));
		LOGGER.info(TranslationMgr.getInstance().getText("me"));
	}
	
	/**
	 * getI18nMessages
	 * @return
	 */
	public ResourceBundle getI18nMessages() {
		return i18nMessages;
	}

	/**
	 * setI18nMessages
	 * @param i18nMessages
	 */
	public void setI18nMessages(ResourceBundle argi18nMessages) {
		i18nMessages = argi18nMessages;
	}
}
