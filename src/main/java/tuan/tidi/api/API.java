package tuan.tidi.api;

public abstract class API {
	public static final String API = "/api/v1";
	
	public static final String AUTH = "/api/v1/auth";
	public static final String LOGIN = "/api/v1/auth/login";
	public static final String REGISTER = "/api/v1/auth/register";
	public static final String RESETPASSWORD = "/api/v1/auth/resetPassword";
	public static final String RESETEMAILVERIFICATION = "/api/v1/auth/resetEmailVerification";
	public static final String EMAILVERIFICATION = "/api/v1/auth/emailVerification";
	public static final String VERIFYTOKEN = "/api/v1/auth/verifyToken";
	
	public static final String ACCOUNT = "/api/v1/account";
	public static final String INFO = "/api/v1/account/info";
	public static final String UPDATEINFO = "/api/v1/account/updateInfo";
	public static final String UPDATEPASSWORD = "/api/v1/account/updatePassword";
	
	public static final String PRODUCT = "/api/v1/product";
	public static final String INDUSTRIES = "/api/v1/product/industry";
	public static final String BRANDS = "/api/v1/product/brand";
	public static final String ALL = "/api/v1/product/all";
	public static final String ONE = "/api/v1/product/one";
	
	public static final String ADMIN = "/api/v1/admin";
	public static final String GETACCOUNTS = "/api/v1/admin/account/all";
	public static final String CREATEACCOUNTS = "/api/v1/admin/account/create";
	public static final String UPDATEACCOUNTS = "/api/v1/admin/account/update";
	public static final String INSERTPRODUCT = "/api/v1/admin/product/insert";
	public static final String UPDATEPRODUCT = "/api/v1/admin/product/update";
	public static final String DELETEPRODUCT = "/api/v1/admin/product/delete";
	public static final String GETALLPRODUCT = "/api/v1/admin/product/all";
	public static final String GETONEPRODUCT = "/api/v1/admin/product/one";
	public static final String GETBRAND = "/api/v1/admin/brand/all";
	public static final String INSERTBRAND = "/api/v1/admin/brand/insert";
	public static final String UPDATEBRAND = "/api/v1/admin/brand/update";
	public static final String GETINDUSTRY = "/api/v1/admin/industry/all";
	public static final String INSERTINDUSTRY = "/api/v1/admin/industry/insert";
	public static final String UPDATEINDUSTRY = "/api/v1/admin/industry/update";
	public static final String GETBRANCH = "/api/v1/admin/branch/all";
	public static final String INSERTBRANCH = "/api/v1/admin/branch/insert";
	public static final String UPDATEBRANCH = "/api/v1/admin/branch/update";
	public static final String GETCATEGORY = "/api/v1/admin/category/all";
	public static final String INSERTCATEGORY = "/api/v1/admin/category/insert";
	public static final String UPDATECATEGORY = "/api/v1/admin/category/update";
	public static final String GETCAMPAIGN = "/api/v1/admin/campaign/all";
	public static final String INSERTCAMPAIGN = "/api/v1/admin/campaign/insert";
	public static final String UPDATECAMPAIGN = "/api/v1/admin/campaign/update";
	public static final String GETCOUPON = "/api/v1/admin/coupon/all";
	public static final String INSERTCOUPON = "/api/v1/admin/coupon/insert";
	public static final String UPDATECOUPON = "/api/v1/admin/coupon/update";
	public static final String GETDISCOUNT = "/api/v1/admin/discount/all";
	public static final String INSERTDISCOUNT = "/api/v1/admin/discount/insert";
	public static final String UPDATEDISCOUNT = "/api/v1/admin/discount/update";
	public static final String GETORDER = "/api/v1/admin/order/all";
	public static final String GETANORDER = "/api/v1/admin/order/one";
	public static final String UPDATEORDER = "/api/v1/admin/order/update";

	public static final String GETITEM = "/api/v1/cart/all";
	public static final String INSERTITEM = "/api/v1/cart/insert";
	public static final String UPDATEITEM = "/api/v1/cart/update";
	public static final String DELETEITEM = "/api/v1/cart/delete";

	public static final String CHECKOUT = "/api/v1/checkout/checkout";
	public static final String GETORDERS = "/api/v1/checkout/all";
	public static final String GETONEORDER = "/api/v1/checkout/one";

}
