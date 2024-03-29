package eu.ibacz.swsc.spring.di.testdependencyinjection.aspect;

import eu.ibacz.swsc.spring.commons.springdemocommons.Notifier;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Aspect
public class BankServiceLoggingAspect {

  private Notifier n;

  public void setN(Notifier n) {
    this.n = n;
  }

    @Around("execution(* eu.ibacz.swsc.spring.di.testdependencyinjection.service.BankService.createNewCustomer(..)) && args(firstname, lastname) )")
    public Object onNewCustomerCreated(ProceedingJoinPoint pjp, String firstname, String lastname) throws Throwable {
        StringBuilder messageBuilder = new StringBuilder("Tesne pred vytvorenim noveho klienta se jmenem '").append(firstname)
                .append("' a prijmenim '").append(lastname).append("'.");
        //System.out.println(messageBuilder.toString());
        n.notify(messageBuilder.toString());

        Object result = pjp.proceed(); //tady se zavola puvodni metoda, na kterou je aspekt zaveseny

        messageBuilder = new StringBuilder("Klient '").append(firstname).append(" ").append(lastname)
                .append("' byl uspesne vytvoren.");
        //System.out.println(messageBuilder.toString());
        n.notify(messageBuilder.toString());

        return result;
    }

    @Around("execution(* eu.ibacz.swsc.spring.di.testdependencyinjection.service.BankService.getAllCustomers(..))")
    public Object onGetAllCustomersInvoked(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("[Aspect]: calling getAllCustomers() START");
        n.notify("[Aspect]: calling getAllCustomers() START");
        Object result = pjp.proceed();  // calling the original method
        //System.out.println("[Aspect]: calling getAllCustomers() END");
        n.notify("[Aspect]: calling getAllCustomers() END");
        return result;
    }
    
}