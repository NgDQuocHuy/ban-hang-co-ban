class App {

    static DOMAIN_API = "http://localhost:9095";
    static BASE_URL_Product = this.DOMAIN_API + "/api/products";

    static AlertMessageEn = class {
        static SUCCESS_CREATED = "Successful data generation !";
        static SUCCESS_UPDATED = "Data update successful !";
        static SUCCESS_DEPOSIT = "Deposit transaction successful !";
        static SUCCESS_WITHDRAW = "Withdrawal transaction successful !";
        static SUCCESS_TRANSFER = "Transfer transaction successful !";
        static SUCCESS_DEACTIVATE = "Deactivate the customer successfully !";

        static ERROR_400 = "The operation failed, please check the data again.";
        static ERROR_401 = "Unauthorized - Your access token has expired or is not valid.";
        static ERROR_403 = "Forbidden - You are not authorized to access this resource.";
        static ERROR_404 = "Not Found - The resource has been removed or does not exist";
        static ERROR_500 = "Internal Server Error - The server system is having problems or cannot be accessed.";

        static ERROR_LOADING_PROVINCE = "Loading list of provinces - cities failed !";
        static ERROR_LOADING_DISTRICT = "Loading list of district - ward failed !"
        static ERROR_LOADING_WARD = "Loading list of wards - communes - towns failed !";
    }

    static AlertMessageVi = class {
        static SUCCESS_CREATED = "Tạo dữ liệu thành công !";
        static SUCCESS_UPDATED = "Cập nhật dữ liệu thành công !";
        static SUCCESS_DEPOSIT = "Giao dịch gửi tiền thành công !";
        static SUCCESS_WITHDRAW = "Giao dịch rút tiền thành công !";
        static SUCCESS_TRANSFER = "Giao dịch chuyển khoản thành công !";
        static SUCCESS_DEACTIVATE = "Hủy kích hoạt khách hàng thành công !";
        static SUCCESS_SUSPENDED = "Tài khoản đã bị chặn khởi hệ thống thành công !";

        static ERROR_CREATED = "Tạo dữ liệu mới không thành công !";
        static ERROR_WITHDRAW = "Số tiền trong tài khoản của quý khách không đủ, vui lòng kiểm tra lại.";
        static ERROR_400 = "Thao tác không thành công, vui lòng kiểm tra lại dữ liệu.";
        static ERROR_401 = "Unauthorized - Access Token của bạn hết hạn hoặc không hợp lệ.";
        static ERROR_403 = "Forbidden - Bạn không được quyền truy cập tài nguyên này.";
        static ERROR_404 = "Not Found - Tài nguyên này đã bị xóa hoặc không tồn tại";
        static ERROR_500 = "Internal Server Error - Hệ thống Server đang có vấn đề hoặc không truy cập được.";

        static ERROR_LOADING_PROVINCE = "Tải danh sách tỉnh - thành phố không thành công !";
        static ERROR_LOADING_DISTRICT = "Tải danh sách quận - phường - huyện không thành công !";
        static ERROR_LOADING_WARD = "Tải danh sách phường - xã - thị trấn không thành công !";
    }

    static SweetAlert = class {

        static showAlertSuccess(t) {
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: t,
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showAlertError(t) {
            Swal.fire({
                position: 'center',
                icon: 'error',
                title: t,
                showConfirmButton: true
            })
        }

        static showSuspendedConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Bạn đồng ý xoá sản phẩm ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có!',
                cancelButtonText: 'Không',
            })
        }
    }

    static IziToast = class {
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }

        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }
    }

    static formatDate(T) {
        return new Date(T).toISOString().
        replace(/T/, ' ').      // replace T with a space
            replace(/\..+/, '').split('T').pop();     // delete the dot and everything after
    }

    static renderProduct(product) {
        let str = `
            <div data-id="${product.id}" class="card col-4 add-cart">
                <div class="card-body">
                    <h6 class="card-title">${product.productName}</h6>
                    <p class="card-subtitle mb-2 text-muted">
                        ${new Intl.NumberFormat('vi-VN', {
                        style: 'currency',
                        currency: 'VND'
                        }).format(product.price)}
                    </p>
                </div>
            </div>
        `;
        return str;
    }

    static renderBill(cartItem) {
        let str = `
            <tr id="tr_${cartItem.id}">
                <td className="col-8" style="padding: 0 10px;">
                    <p>${cartItem.productName}</p>
                </td>
                <td className="col-4">
                    <p>Số Lượng: <input id="quantity_${cartItem.id}" style="width: 30px" value="${cartItem.quantity}" readonly></p>
                    <span>${new Intl.NumberFormat('vi-VN', {
                        style: 'currency',
                        currency: 'VND'
                        }).format(cartItem.productPrice)}
                    </span>
                </td>
                <td>    
                    <button class="minus" data-id="${cartItem.id}">-</button>
                    <button class="removeCart" data-id="${cartItem.id}">x</button>
                </td>
            </tr>
        `;
        return str;
    }

    static renderErrorCre(errors) {
        let str = "<div class='bg-danger' style='margin-top: 20px; padding: 10px 0'><ul>";
        for (let i = 0; i < errors.length; i++) {
            str += `<li><h4>${errors[i]}</h4></li>`;
        }
        str += "</ul></div>";
        return str;
    }

    static renderErrorUp(errors) {
        let str = "<div class='bg-danger' style='margin-top: 20px; padding: 10px 0'><ul>";
        for (let i = 0; i < errors.length; i++) {
            str += `<li><h4>${errors[i]}</h4></li>`;
        }
        str += "</ul></div>";
        return str;
    }
}

class Product {
    constructor(id, productName, price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }
}

class CartItem {
    constructor() {
    }
}
