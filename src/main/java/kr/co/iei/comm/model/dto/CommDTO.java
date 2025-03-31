package kr.co.iei.comm.model.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias(value="comm")
public class CommDTO {
private int commNo;
private int reviewNo;
private String commContent;
}
