package cn.com.dingduoduo.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class EnvironmentPlaceHolderConfiguer extends PropertyPlaceholderConfigurer
		implements EnvironmentAware {

	Environment env;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);

		// 若转换失败,启动时就让它报错
		ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) env;

		configurableEnvironment.getPropertySources().addFirst(new PropertiesPropertySource("customized-cys", props));
	}

	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
	}
}

