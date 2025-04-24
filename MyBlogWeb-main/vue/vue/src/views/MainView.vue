<template>
  <div class="home">
    <el-container >
      <el-header style="padding: 0">
        <el-menu class="el-menu-demo"
                 mode="horizontal"
                 active-text-color="green"
                 router>
          <el-menu-item>
            <img src="../assets/logo.png" alt="" style="height: 35px">
          </el-menu-item>
          <el-menu-item index="/manage/home">首页</el-menu-item>
          <el-menu-item index="/manage/mymsg">消息中心</el-menu-item>
          <el-submenu index="3">
            <template slot="title">我的</template>
            <el-menu-item index="/manage/myinfo">个人信息</el-menu-item>
            <el-menu-item index="/manage/myarticle">我的文章</el-menu-item>
            <el-menu-item index="/login">退出</el-menu-item>
          </el-submenu>
          <el-menu-item index="/manage/myeditor"
                        style="float: right; margin-right: 30px;">
            <el-button type="success" round>
              <i class="el-icon-circle-plus-outline" style="color: white"></i>
              发布文章
            </el-button>
          </el-menu-item>
        </el-menu>
      </el-header>

      <!-- main -->
      <el-container>
        <!-- 内容 -->
        <el-main style="border: 1px solid #ccc;">
          <router-view />
        </el-main>
      </el-container>

    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
  export default {
    name: 'MainView',
    components: {

    },
    data() {
      return {
          //Menu 侧边导航
        sideWidth:200,
        isCollapse: false,
          //header标签
        collapseBtnClass: 'el-icon-s-fold',

      }
    },
  }
</script>

<style>

</style>