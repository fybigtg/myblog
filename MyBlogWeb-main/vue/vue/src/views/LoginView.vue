<template>
  <div class="login">
    <div style="height: 150px"></div>
    <!--登陆表单-->
    <div class="loginContainer"
         style="
         box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
         margin : auto auto;
         height: 350px;
         background-color: lightblue;
         width: 600px">
      <h1 style="text-align: center;
                 line-height: 90px;
                 color: cadetblue;
                 font-size: 40px;
                 font-family: Microsoft YaHei,serif">MyBlog LOGIN</h1>
      <el-form :rules="loginRules" :model="user">
        <el-form-item prop="username">
          <span style="margin-left: 120px">账 号</span>
          <el-input prop="username" v-model="user.username" placeholder="请输入账号" @keyup.enter="loginAccount"
                    style="width: 50%;margin-top: 30px;margin-left: 20px;color: lightblue;">
            <i slot="prefix" class="el-input__icon el-icon-user"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <span style="margin-left: 120px">密 码</span>
          <el-input placeholder="请输入密码" v-model="user.password" @keyup.enter="loginAccount" show-password
                    style="width: 50%;margin-top: 0;margin-left: 20px;color: lightblue;">
            <i slot="prefix" class="el-input__icon el-icon-lock"></i>
          </el-input>
        </el-form-item>
        <div style="margin-top: 10px; margin-left: 250px">
          <el-button style="" type="text" @click="handleFgPd">忘记密码？</el-button>
          <span style="color: grey; font-size: small">| </span>
          <el-button style="width: 0" type="text" size="small" @click="isShowSignUp = true">注册</el-button>
        </div>
        <el-button @click="loginAccount" style="background-color: lightskyblue;border: 0; margin-top: 20px; margin-left: 260px; height: 40px; width: 80px; font-size: 20px">
          登 录
        </el-button>
      </el-form>
    </div>
    <!--注册表单-->
    <div>
      <el-dialog title="注册信息" :visible.sync="isShowSignUp"
                 style="width: 1000px; overflow: hidden; margin : auto auto;">
        <el-form :model="ruleForm" :rules="signUpRules" label-width="100px" style="width: 90%">
          <el-form-item label="用户名 :" prop="username" required>
            <el-input v-model="ruleForm.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" required>
            <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPassword" required>
            <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称 :" prop="nickname" required>
            <el-input v-model="ruleForm.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱 :" prop="email">
            <el-input v-model="ruleForm.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="联系方式 :" prop="phone">
            <el-input v-model="ruleForm.phone" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="isShowSignUp = false">取 消</el-button>
          <el-button type="primary" @click="handleForm">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--忘记密码？-->
    <div>
      <el-dialog
          title="活该！哈哈哈哈哈哈哈哈哈！！"
          :visible.sync="fgPdDialogVisible"
          width="350px">
        <img style="margin-left: 60px; height: 140px" src="../assets/laughingcat.jpg" alt=""/>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" size="medium" @click="fgPdDialogVisible = false"> 666 </el-button>
        </span>
      </el-dialog>
    </div>

  </div>
</template>


<script>
  export default {
    name: 'LoginView',
    data() {
      return {
          //登录表单
        loginRules: {
          username: [{required: true, message: '用户名不能为空', trigger: 'blur'}],
          password: [{required: true, message: '密码不能为空', trigger: 'blur'}],
        },
        user:{
          username:'',
          password:'',
        },
          //注册表单
        isShowSignUp:false,
        signUpRules:{
          username:[{required: true, message: '用户名不能为空', trigger: 'blur'},
            {pattern: /^[a-zA-Z0-9_-]{1,20}$/ ,message: '长度为20，仅包含字母、数字、下划线和减号', trigger: 'blur'}],
          password:[{required: true, message: '密码不能为空', trigger: 'blur'},
            {pattern: /^[a-zA-Z0-9_-]{1,20}$/ ,message: '长度为20，仅包含字母、数字、下划线和减号', trigger: 'blur'}],
          checkPassword:[{required: true, message: '密码不能为空', trigger: 'blur'}],
          nickname:[{required: true, message: '昵称不能为空', trigger: 'blur'}],
          email:[{type: 'email', message:'请输入正确的格式', trigger: 'blur'}],
          phone:[],
        },
        ruleForm: {
          username: '',
          password: '',
          checkPassword: '',
          nickname: '',
          email: '',
          phone: '',
        },
          //忘记密码？
        fgPdDialogVisible: false,
      }
    },
    methods:{
      //忘记密码？
      handleFgPd(){
        this.fgPdDialogVisible = true
      },
      //表单校验和提交
      handleForm(){
        if(this.ruleForm.username.trim() === ''){
          this.$message({message: '用户名不能为空！', type: 'error'})
          return
        }
        if(this.ruleForm.password.trim() === '' || this.ruleForm.checkPassword.trim() === ''){
          this.$message({message: '密码不能为空！', type: 'error'})
          return
        }
        if(this.ruleForm.nickname.trim() === ''){
          this.$message({message: '昵称不能为空！', type: 'error'})
          return
        }
        if(this.ruleForm.password !== this.ruleForm.checkPassword){
          this.$message({message: '两次密码不一致', type: 'error'})
          return
        }
        this.saveUser()
      },
      saveUser(){
        let form = {
          username: this.ruleForm.username,
          passwd: this.ruleForm.password,
          nickname: this.ruleForm.nickname,
          email: this.ruleForm.email,
          phone: this.ruleForm.phone
        }
        this.request.post(this.gotoUrl+"/login/signup",form).then(res => {
          if(res.code === '1'){
            this.$message({message: '注册成功！', type:'success'})
            setTimeout(()=>{location.reload()},1000)
          }else{
            this.$message({message: res.msg, type: 'error'})
          }
        })
      },
      //登录账户
      loginAccount(){
        let loginAccount={
          username: this.user.username,
          passwd: this.user.password
        }
        if(this.user.username.trim() === ''){
          this.$message({
            message: '请输入账号!',
            type: 'warning'
          })
          }
        else if(this.user.password.trim() === ''){
          this.$message({
            message: '请输入密码!',
            type: 'warning'
          })
        }
        else {
          this.request.post(this.gotoUrl+"/login",loginAccount).then(res => {
            if(res.code === '1'){
              this.$message({
                message: '登录成功',
                type:'success'
              })
              /*this.$store.commit('setToken',res.data.token)
              this.$store.commit('setUser',res.data.user)*/
              let accountUser = {
                id: res.data.id,
                token: res.data.token
              }
              localStorage.setItem('accountUser', JSON.stringify(accountUser))
              setTimeout(()=>this.$router.push({path:'/manage',query:{account: accountUser}}),500)
            }
            else {
              this.$message({
                message: res.msg,
                type: 'warning'
              })
            }
          })
        }
      }
    },

  }
</script>

<style>
  .login {
    background-image: linear-gradient(to bottom right, darkseagreen, wheat);
    height: 100vh;
    overflow: hidden;
  }
  .loginContainer .el-form-item__error{
    margin-left: 175px;
  }

</style>
