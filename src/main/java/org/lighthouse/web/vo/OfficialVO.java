package org.lighthouse.web.vo;

import java.util.List;
import java.util.Map;

/**
 * @author nivanov
 * on 03.11.2017.
 */

public class OfficialVO {
    private String name;
    private String function;
    private String country;
    private Map<Integer, List<DeclarationVO>> declarations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<Integer, List<DeclarationVO>> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(Map<Integer, List<DeclarationVO>> declarations) {
        this.declarations = declarations;
    }
}
