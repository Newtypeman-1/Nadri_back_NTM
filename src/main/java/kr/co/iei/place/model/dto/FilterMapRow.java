package kr.co.iei.place.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Alias(value="row")
public class FilterMapRow {
    private String code;
    private String codeType;
    private int placeTypeId;

}
