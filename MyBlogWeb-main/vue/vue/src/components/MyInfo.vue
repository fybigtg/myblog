<template>
  <div style="
         box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
         margin : 0 auto;
         height: 300px;
         background-color: whitesmoke;
         width: 600px">
    <div style="padding: 30px">
      <el-form label-width="100px" :rules="rules" :model="accountUser"  style="width: 100%;">
        <el-form-item label="昵称 :" prop="nickname">
          <el-input :disabled="isEditable" v-model="accountUser.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱 :" prop="email">
          <el-input :disabled="isEditable" v-model="accountUser.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式 :" >
          <el-input :disabled="isEditable" v-model="accountUser.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="float: right; margin-right: 20px">
        <el-button v-show="!isEditable" type="success" @click="saveInfo">提 交</el-button>
        <el-button type="primary" @click="isEditable=false">编 辑</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "MyInfo",
    data() {
      return {
        accountUser: {},
        isEditable: true,
        rules: {
          nickname:[{required: true, message: '昵称不能为空', trigger: 'blur'}],
          email:[{type: 'email', message:'请输入正确的格式', trigger: 'blur'}],
        }
      }
    },
    created(){
      this.getAccount()
    },
    mounted(){
      this.getAccount()
    },
    methods:{
      saveInfo(){
        if(this.accountUser.nickname === ''){
          this.isEditable = true
          this.$message({
            type:'error',
            message:'昵称不能为空！'
          })
          setTimeout(()=>{
            location.reload()
          },1000)
          return
        }
        this.isEditable = true
        this.request.post(this.gotoUrl+"/users", this.accountUser).then(res => {
          if(res.code === '1'){
            this.$message({
              type:'success',
              message: '保存成功!'
            })
            setTimeout(()=>{
              location.reload()
            },1000)
          }
          else {
            this.$message({
              type:'error',
              message: '保存失败！'
            })
          }
        })
      },
      getAccount(){
        this.request.get(this.gotoUrl+"/login/account",{
          params:{
            id: JSON.parse(localStorage.getItem('accountUser')).id
          }
        }).then(res=>{
          this.accountUser={
            id: res.data.id,
            username: res.data.username,
            nickname: res.data.nickname,
            email: res.data.email,
            phone: res.data.phone,
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>