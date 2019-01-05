package com.mmall.pojo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//原先的equals和hashCode方法只针对id，故这里采用(of = "id")
@EqualsAndHashCode(of = "id")
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;


    //这里的equals和hashCode方法比较简单，所以可以使用lombok替换。
    //复杂的equals和hashCode方法不推荐使用lombok。
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)    return true;
//        if (obj == null || getClass() != obj.getClass())    return false;
//
//        Category category = (Category) obj;
//
//        return !(id != null ? !id.equals(category.id) : category.id != null);
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }

}