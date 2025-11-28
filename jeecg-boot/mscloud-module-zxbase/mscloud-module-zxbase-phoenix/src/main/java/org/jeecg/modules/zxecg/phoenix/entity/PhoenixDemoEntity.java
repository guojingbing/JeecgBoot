package org.jeecg.modules.zxecg.phoenix.entity;

import lombok.Data;
import org.jeecg.modules.zxecg.phoenix.annotation.PhoenixEntityAnnotation;

import java.io.Serializable;

@PhoenixEntityAnnotation.ClassAnnotation(table = "PHOENIX_DEMO",indexs={"IDXDEMO1,ID","IDXDEMO2,ID,TYPE"})
@Data
public class PhoenixDemoEntity implements Serializable {
    @PhoenixEntityAnnotation.FieldAnnotation(column = "ID", pkNum = 1, sequence = "SEQ_REP_MATCH_KEY")
    private Long id;
    @PhoenixEntityAnnotation.FieldAnnotation(column = "TYPE", pkNum = 2)
    private Short type;
    @PhoenixEntityAnnotation.FieldAnnotation(column = "NAME", cf="s")
    private String name;
    @PhoenixEntityAnnotation.FieldAnnotation(column = "REMARK", cf="s")
    private String remark;
}
