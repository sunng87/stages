package info.sunng.stages;

import java.util.Collection;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/17/11
 * Time: 9:44 AM
 */
public interface TaskContext {

    public <T> T getAttribute(String name, Class<T> clazz);

    public void setAttribute(String name, Object value);

    public Collection<String> getAttributeNames();

}
