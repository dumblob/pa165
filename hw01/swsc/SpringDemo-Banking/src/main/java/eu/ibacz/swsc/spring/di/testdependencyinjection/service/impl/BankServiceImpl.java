package eu.ibacz.swsc.spring.di.testdependencyinjection.service.impl;

import eu.ibacz.swsc.spring.di.testdependencyinjection.dao.CustomerDao;
import eu.ibacz.swsc.spring.di.testdependencyinjection.dto.Customer;
import eu.ibacz.swsc.spring.di.testdependencyinjection.service.BankService;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BankServiceImpl implements BankService {
       
    //private DataSource dataSource;
    private CustomerDao custDao;

    public List<Customer> getAllCustomers() {
        //CustomerDao dao = new PureJdbcCustomerDaoImpl();
        //dao.setDataSource(dataSource);
        return custDao.findAll();
    }

    public Customer createNewCustomer(String firstname, String lastname) {
        Customer customer = new Customer();
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        //CustomerDao dao = new PureJdbcCustomerDaoImpl();
        //dao.setDataSource(dataSource);
        custDao.save(customer);
        return customer;
    }

  public void setCustDao(CustomerDao custDao) {
    this.custDao = custDao;
  }

}
