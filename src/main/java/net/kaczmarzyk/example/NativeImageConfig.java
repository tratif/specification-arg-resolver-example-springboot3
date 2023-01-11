package net.kaczmarzyk.example;

import net.kaczmarzyk.spring.data.jpa.nativeimage.SpecificationArgumentResolverHintRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints({SpecificationArgumentResolverHintRegistrar.class, MyProjectSpecificationArgumentResolverProxyHintRegistrar.class})
public class NativeImageConfig {
}
