package net.kaczmarzyk.example;

import net.kaczmarzyk.spring.data.jpa.nativeimage.SpecificationArgumentResolverProxyHintRegistrar;

public class MyProjectSpecificationArgumentResolverProxyHintRegistrar extends SpecificationArgumentResolverProxyHintRegistrar {
	private MyProjectSpecificationArgumentResolverProxyHintRegistrar() {
		super("net.kaczmarzyk.example");
	}
}
