<template>
  <div>
    <div style="margin-bottom: 10px">
      <span aria-required="true">文章标题：</span>
      <el-input v-model="titlename" placeholder="请输入标题" style="width: 500px"></el-input>
    </div>

    <mavon-editor :toolbars="markdownOption" v-model="value" />
    <div>.</div>
    <el-button type="success"
               style="float: right"
               size="medium"
               @click="publish"
               round>确认发表</el-button>
  </div>
</template>

<script>
  export default{
    name: 'MyEditor',
    data(){
      return{
        titlename:'',
        value: '',
        markdownOption:{
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: true, // 标记
          superscript: true, // 上角标
          subscript: true, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          imagelink: false, // 图片链接
          code: true, // code
          table: true, // 表格
          fullscreen: true, // 全屏编辑
          readmodel: true, // 沉浸式阅读
          htmlcode: true, // 展示html源码
          help: true, // 帮助
          /* 1.3.5 */
          undo: true, // 上一步
          redo: true, // 下一步
          trash: true, // 清空
          save: false, // 保存（触发events中的save事件）
          /* 1.4.2 */
          navigation: true, // 导航目录
          /* 2.1.8 */
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          /* 2.2.1 */
          subfield: true, // 单双栏模式
          preview: true, // 预览
        }
      }
    },
    methods:{
      publish(){
        if(this.titlename.trim() === ''){
          //console.log("blank title!")
          this.$message({
            message: '请输入标题！',
            type: 'error'
          })
        }else{
          let id = JSON.parse(localStorage.getItem("accountUser")).id
          let param={
            currid: id,
            filename: this.titlename,
            type: 'md',
            content: this.value
          }
          this.request.post(this.gotoUrl+"/file/upload",param).then(res => {
            if(res.code === '1') {
              this.$message({
                message: '发布成功',
                type: 'success'
              })
            }else {
              this.$message({
                message: '发布失败',
                type: 'error'
              })
            }
          })
        }
      }
    }
  }
</script>