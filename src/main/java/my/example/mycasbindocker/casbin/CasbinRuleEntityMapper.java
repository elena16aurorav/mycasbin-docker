package my.example.mycasbindocker.casbin;

import my.example.mycasbindocker.entity.CasbinRuleEntity;
import org.apache.ibatis.annotations.*;
import org.casbin.jcasbin.model.Model;

import java.util.List;

@Mapper
public interface CasbinRuleEntityMapper {

    public static final String TABLE_NAME = "casbin_rule";

    @Select("select * from "+ TABLE_NAME)
    List<CasbinRuleEntity> loadAll();

    @Update("declare " +
            "nCount NUMBER;" +
            "v_sql LONG;" +
            "begin " +
            "SELECT count(*) into nCount FROM USER_TABLES where table_name = '"+ TABLE_NAME +"';" +
            "IF(nCount <= 0) " +
            "THEN " +
            "v_sql:='" +
            "CREATE TABLE " + TABLE_NAME + " " +
            "                    (ptype VARCHAR(100) not NULL, " +
            "                     v0 VARCHAR(100), " +
            "                     v1 VARCHAR(100), " +
            "                     v2 VARCHAR(100), " +
            "                     v3 VARCHAR(100)," +
            "                     v4 VARCHAR(100)," +
            "                     v5 VARCHAR(100))';" +
            "execute immediate v_sql;" +
            "END IF;" +
            "end;")
    void createTable();

    @Update("declare " +
            "nCount NUMBER;" +
            "v_sql LONG;" +
            "begin " +
            "SELECT count(*) into nCount FROM dba_tables where table_name = '${tableName}';" +
            "IF(nCount >= 1) " +
            "THEN " +
            "v_sql:='drop table " + TABLE_NAME + "';" +
            "execute immediate v_sql;" +
            "END IF;" +
            "end;")
    void dropTable();

    @Insert("INSERT INTO " + TABLE_NAME
            + " (ptype,v0,v1,v2,v3,v4,v5) VALUES (#{ptype},#{v0},#{v1},#{v2},#{v3},#{v4},#{v5})")
    void insertData(CasbinRuleEntity entity);

    @Insert("<script>"  +
            "DELETE FROM " + TABLE_NAME + " WHERE ptype = #{ptype}" +
            "<foreach collection=\"list\" item=\"item1\" index=\"index\"  separator=\" \">" +
            " AND v#{index} = #{item1}" +
            "</foreach>" +
            "</script>")
    void deleteData(@Param("ptype") String ptype, @Param("list") List<String> rules);
}
