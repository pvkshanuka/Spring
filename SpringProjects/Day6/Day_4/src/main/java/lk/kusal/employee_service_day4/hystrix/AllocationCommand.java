package lk.kusal.employee_service_day4.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lk.kusal.employee_service_day4.Model.Employee;
import lk.kusal.employee_service_day4.common_model.Allocation;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AllocationCommand extends HystrixCommand<Allocation[]> {

    Employee employee;
    HttpHeaders httpHeaders;
    RestTemplate restTemplate;

    public AllocationCommand(Employee employee, HttpHeaders httpHeaders, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("default"));
        this.employee = employee;
        this.httpHeaders = httpHeaders;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Allocation[] run() throws Exception {

        HttpEntity<String>httpEntity =new HttpEntity<>("",httpHeaders);

        ResponseEntity<Allocation[]> responseEntity = restTemplate.exchange("http://alloservice/alloservice/fetch_f_emp/" + employee.getId(), HttpMethod.POST, httpEntity, Allocation[].class);

        return responseEntity.getBody();
    }

    @Override
    protected Allocation[] getFallback() {
        return new Allocation[1];
    }
}
