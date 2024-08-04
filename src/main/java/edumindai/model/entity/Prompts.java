package edumindai.model.entity;


import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName prompts
 */
@Data
public class Prompts implements Serializable {
    /**
     * 提示词id
     */

    private Integer id;

    /**
     * 提示词
     */
    private String prompt;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Prompts other = (Prompts) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPrompt() == null ? other.getPrompt() == null : this.getPrompt().equals(other.getPrompt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPrompt() == null) ? 0 : getPrompt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", prompt=").append(prompt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}