package fr.maxlego08.worldprotection.placeholder;
@FunctionalInterface
public interface ReturnConsumer<T, G> {

	G accept(T t);
	
}