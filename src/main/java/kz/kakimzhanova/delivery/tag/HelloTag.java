package kz.kakimzhanova.delivery.tag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class HelloTag extends TagSupport {
    private String name;
    private String language;
    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String greeting = null;
            if (language.equals("ru_RU")){
                greeting = "Здравствуйте, " + name + "! Добро пожаловать на Food delivery. Ваш заказ будет доставлен в течение 30 минут.";
            }
            if (language.equals("en_US")){
                greeting = "Hello, " + name + "! Welcome to Food delivery. Your order will be delivered in 30 minutes.";
            }
            pageContext.getOut().write(greeting);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }
}
