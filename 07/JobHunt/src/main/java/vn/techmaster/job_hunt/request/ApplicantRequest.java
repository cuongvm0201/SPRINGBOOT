package vn.techmaster.job_hunt.request;

import java.util.List;

import javax.validation.constraints.*;

import vn.techmaster.job_hunt.model.Skill;

/**
 * Thêm sửa xoá Applicant:
 * - Chọn job từ danh sách job hiện có
 * - Họ và tên
 * - Email
 * - Điện thoại
 * - Mô tả kỹ năng
 */

public record ApplicantRequest(
                String id,

                String job_id,

                @NotBlank(message = "Name cannot null") String name,

                @NotBlank(message = "Email cannot null") @Email(message = "Invalid email") String email,

                @NotBlank(message = "Phone cannot null") String phone,

                @NotNull(message = "Skill cannot null") List<Skill> skills) {
}