package com.aaa.springboothomestay.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;


public class JwtUtil {
   /* *//**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param ttlMillis jwt过期时间
     * @param user      登录成功的user对象
     * @return
     *//*
    public static String createJWT(long ttlMillis, Student user) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", user.getStuno());
        claims.put("username", user.getStuname());
        claims.put("password", user.getIdcard());

        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = user.getIdcard();

        //生成签发人
        String subject = user.getStuname();



        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    *//**
     * Token的解密
     * @param token 加密后的token
     * @param user  用户的对象
     * @return
     *//*
    public static Claims parseJWT(String token, Student user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getIdcard();

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }


    *//**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     * @param token
     * @param user
     * @return
     *//*
    public static Boolean isVerify(String token, Student user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getIdcard();

        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();

        if (claims.get("password").equals(user.getIdcard())) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Student student=new Student();
        student.setStuname("陈天昊");
        student.setClassid(1);
        student.setIdcard("410725199810040817");
        student.setPhone(13938220549L);
        student.setState(1);
        student.setStuno("1");
        String jwt = JwtUtil.createJWT(60 * 60 * 24, student);
        System.out.println(jwt);


        Student student1=new Student();

        student1.setIdcard("410725199810040818");
      //  System.out.println(JwtUtil.isVerify(jwt,student1));
        System.out.println(JwtUtil.parseJWT(jwt,student1));
    }*/

    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /*
    * 生成jwt
    * */
    public static String setJwtToken(String id, Object t){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("guli-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("id", id)
                .claim("nickname", t)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        jwtToken="1001";
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
    public static Claims getMemberIdByJwtToken(String request) {

        if(StringUtils.isEmpty(request)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(request);
        Claims claims = claimsJws.getBody();
        return claims;
    }

   public static  Object getJsonString(Object object){
       Object o = JSONObject.toJSON(object);
       return o;
   }

   public static <T> T getClass(Claims claims, Class<T> t){
       Map map = claims.get("nickname", Map.class);
       System.out.println(map);
       String s = JSON.toJSONString(map);
       //net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(map);
       JSONObject jsonObject1 = JSONObject.parseObject( s);
       T t1= jsonObject1.toJavaObject(t);

       return  t1;
   }

    public static void main(String[] args) {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE1OTU5MTk2ODUsImV4cCI6MTU5NjAwNjA4NSwiaWQiOiIxIiwibmlja25hbWUiOnsiaWQiOjEsIm5hbWUiOiIxMTEiLCJzZXgiOjEsImlkY2FyZCI6IjQxMDcyNTE5OTgxMDA0MDgxNyIsInBob25lIjoiMTM5MzgyMjA1NDkiLCJwcm92aW5jZSI6Iuays-WNl-ecgSIsImNpdHkiOiLmlrDkuaHluIIiLCJhZGRyZXNzIjoi5rKz5Y2X55yB5paw5Lmh5biC5Y6f6Ziz5Y6_5bmz5Y6f5aSn6YGT6b6Z5rqQ56S-5Yy6Iiwic3RhdHVzIjoxfX0.LkfGFD2OBMerAXqIEJzMTjS68VbqdBF6FfFeB0gO_wU";
        Claims claims = JwtUtil.getMemberIdByJwtToken(token);
        //JwtUtil.getClass(claims,Person.class);
        System.out.println(claims);
    }


}
