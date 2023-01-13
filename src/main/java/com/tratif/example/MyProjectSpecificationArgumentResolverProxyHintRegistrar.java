package com.tratif.example;

import net.kaczmarzyk.spring.data.jpa.nativeimage.SpecificationArgumentResolverProxyHintRegistrar;

public class MyProjectSpecificationArgumentResolverProxyHintRegistrar extends SpecificationArgumentResolverProxyHintRegistrar {
	private MyProjectSpecificationArgumentResolverProxyHintRegistrar() {
		super("com.tratif.example");
	}
}
