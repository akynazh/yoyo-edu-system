package xdu.zh.yoyoinflux.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author jiangzhh
 * @date 2024/4/17
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String cardId;
    private String name;
    private String major;
    private String college;
    private String department;
    private String classId;
}
