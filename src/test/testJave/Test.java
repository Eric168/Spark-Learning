package testJave;

import com.eric.self.UserAuthoirtyApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfeng on 18/11/6.
 */
public class Test {

    public static void main(String[] args) {

        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath*:springconfig/*.xml");
        UserAuthoirtyApplication userAuthoirtyApplication=(UserAuthoirtyApplication)appContext.getBean("userAuthoirtyApplication");
        List<Object> list=new ArrayList<>();
        //userAuthoirtyApplication.insert(list);
        System.out.println("");
    }

}
