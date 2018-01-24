package fede.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FedeCollections
{
	public static <T> List<T> filter(List<T> list, Predicate<T> predicate)
	{
		return list.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public static <T> boolean any(List<T> list, Predicate<T> predicate)
	{
		return list.stream().anyMatch(predicate);
	}
}
