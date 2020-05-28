package geektime.spring.data.simplejdbcdemo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Foo {
    public Long id;
    public String bar;
}
