package cn.itcast.order.zside;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class siderecall {
    @RequestMapping("/health")
    public Mono<String> health(){

        //health接口
        Mono<String> mono =Mono.just ( "{\n"+
                "\"status\":\"UP\"\n"+
                "}" );
        return mono;
    }


    @RequestMapping(value = "/query",method =  RequestMethod.GET)
    public String query(){
        return "query";
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){

    return "list";

        }


}
