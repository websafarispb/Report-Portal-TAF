package com.reportportal.ta.annotations;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Initialization of page object.
 * Classes marked annotation {@link PageObject}
 * will be initialized with WebDriver as page objects
 */
@Component
public class PageObjectBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    @Lazy
    private WebDriverWrapper webDriverWrapper;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(PageObject.class)) {
            PageFactory.initElements(webDriverWrapper, bean);
        }
        return bean;
    }
}
