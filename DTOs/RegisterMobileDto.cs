using System.ComponentModel.DataAnnotations;

namespace DecalXeAPI.DTOs
{
    public class RegisterMobileDto
    {
        [Required(ErrorMessage = "Họ và tên là bắt buộc.")]
        [MaxLength(100, ErrorMessage = "Họ và tên không được vượt quá 100 ký tự.")]
        public string FullName { get; set; } = string.Empty;

        [Required(ErrorMessage = "Tên đăng nhập là bắt buộc.")]
        [MaxLength(100, ErrorMessage = "Tên đăng nhập không được vượt quá 100 ký tự.")]
        public string Username { get; set; } = string.Empty;

        [Required(ErrorMessage = "Email là bắt buộc.")]
        [EmailAddress(ErrorMessage = "Email không hợp lệ.")]
        [MaxLength(100, ErrorMessage = "Email không được vượt quá 100 ký tự.")]
        public string Email { get; set; } = string.Empty;

        [Required(ErrorMessage = "Mật khẩu là bắt buộc.")]
        [MinLength(6, ErrorMessage = "Mật khẩu phải có ít nhất 6 ký tự.")]
        [MaxLength(50, ErrorMessage = "Mật khẩu không được vượt quá 50 ký tự.")]
        public string Password { get; set; } = string.Empty;

        [Required(ErrorMessage = "Xác nhận mật khẩu là bắt buộc.")]
        [Compare("Password", ErrorMessage = "Xác nhận mật khẩu không khớp với mật khẩu.")]
        public string ConfirmPassword { get; set; } = string.Empty;
    }
}
