package my.example.mycasbindocker.casbin;

import lombok.RequiredArgsConstructor;
import my.example.mycasbindocker.entity.CasbinRuleEntity;
import org.apache.commons.collections.CollectionUtils;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * MybatisAdapter is the Mybatis adapter for jCasbin.
 * It can load policy from Mybatis supported database or save policy to it.
 */
@Component
@RequiredArgsConstructor
public class CasbinAdapter implements Adapter {

    @Autowired
    CasbinRuleEntityMapper mapper;

    /**
     * loadPolicy loads all policy rules from the storage.
     */
    @Override
    public void loadPolicy(Model model) {
        List<CasbinRuleEntity> casbinRules = mapper.loadAll();
        System.out.println(casbinRules.size());
        for (CasbinRuleEntity line : casbinRules) {
            loadPolicyLine(line, model);
        }
    }

    private static void loadPolicyLine(CasbinRuleEntity line, Model model){
        String lineText = line.getPtype();
        if (line.getV0() != null) {
            lineText += ", " + line.getV0();
        }
        if (line.getV1() != null) {
            lineText += ", " + line.getV1();
        }
        if (line.getV2() != null) {
            lineText += ", " + line.getV2();
        }
        if (line.getV3() != null) {
            lineText += ", " + line.getV3();
        }
        if (line.getV4() != null) {
            lineText += ", " + line.getV4();
        }
        if (line.getV5() != null) {
            lineText += ", " + line.getV5();
        }
        Helper.loadPolicyLine(lineText,model);
    }

    /**
     * savePolicy saves all policy rules to the storage.
     */
    @Override
    public void savePolicy(Model model) {
        for (Map.Entry<String, Assertion> entry : model.model.get("p").entrySet()) {
            String ptype = entry.getKey();
            Assertion ast = entry.getValue();

            for (List<String> rule : ast.policy) {
                CasbinRuleEntity line = savePolicyLine(ptype, rule);
                mapper.insertData(line);
            }
        }

        for (Map.Entry<String, Assertion> entry : model.model.get("g").entrySet()) {
            String ptype = entry.getKey();
            Assertion ast = entry.getValue();

            for (List<String> rule : ast.policy) {
                CasbinRuleEntity line = savePolicyLine(ptype, rule);
                mapper.insertData(line);
            }
        }
    }

    private CasbinRuleEntity savePolicyLine(String ptype, List<String> rule) {
        CasbinRuleEntity entity = new CasbinRuleEntity();

        entity.setPtype(ptype);
        if (rule.size() > 0) {
            entity.setV0(rule.get(0));
        }
        if (rule.size() > 1) {
            entity.setV1(rule.get(1));
        }
        if (rule.size() > 2) {
            entity.setV2(rule.get(2));
        }
        if (rule.size() > 3) {
            entity.setV3(rule.get(3));
        }
        if (rule.size() > 4) {
            entity.setV4(rule.get(4));
        }
        if (rule.size() > 5) {
            entity.setV5(rule.get(5));
        }

        return entity;
    }

    /**
     * addPolicy adds a policy rule to the storage.
     */
    @Override
    public void addPolicy(String sec, String ptype, List<String> rule) {
        if(CollectionUtils.isEmpty(rule)) return;
        CasbinRuleEntity entity = savePolicyLine(ptype, rule);
        mapper.insertData(entity);
    }

    /**
     * removePolicy removes a policy rule from the storage.
     */
    @Override
    public void removePolicy(String sec, String ptype, List<String> rule) {
        if(CollectionUtils.isEmpty(rule)) return;
        removeFilteredPolicy(sec, ptype, 0, rule.toArray(new String[0]));
    }

    /**
     * removeFilteredPolicy removes policy rules that match the filter from the storage.
     */
    @Override
    public void removeFilteredPolicy(String sec, String ptype, int fieldIndex, String... fieldValues) {
        List<String> values = Optional.ofNullable(Arrays.asList(fieldValues)).orElse(new ArrayList<>());
        if(CollectionUtils.isEmpty(values)) return;

        mapper.deleteData(ptype, values);
    }
}
