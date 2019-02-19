package se.r2m.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootResource {

  private static final String template = "Konnichiwa, %s!";

  @GetMapping("/")
  public Hello hello(@RequestParam(value="name", defaultValue = "R2M") String name) {
      return new Hello(String.format(template, name));
  }

}
