<template>
  <div>
    <div>
      <el-input
          placeholder="请输入关键字"
          prefix-icon="el-icon-search"
          v-model="keyword"
          style="width: 30%">
      </el-input>
      <el-button class="el-icon-search"
                 type="success"
                 @click="loadFile"
                 plain
                 style="margin-left: 10px">筛选</el-button>
    </div>
    <div>
      <router-view />
    </div>


    <el-table
        :data="tableData"
        @row-click="rowClick"
        stripe
        size="medium"
        style="width: 100%">
      <el-table-column
          prop="filename"
          label="标题">
      </el-table-column>
      <el-table-column
          prop="writer"
          label="作者"
          width="300">
      </el-table-column>
      <el-table-column
          prop="date"
          label="发布日期"
          width="300">
      </el-table-column>
    </el-table>

    <div class="block" style="margin-top: 10px">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          layout="prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import router from "@/router";

  export default{
    name: 'Home',
    data(){
      return{
        tableData:[],
        keyword:'',

        total: 10,
        pageSize: 10,
        pageNum: 1,
      }
    },
    created() {
      this.loadFile()
    },
    mounted() {
      this.loadFile()
    },
    methods:{
      rowClick(row,event,colum){
        this.showArticle(row.id,row.uuid)
      },
      handleCurrentChange(val){
        this.pageNum=val
      },
      loadFile(){
        this.request.get(this.gotoUrl+'/file/page',{
          params:{
            pageNum: this.pageNum,
            pageSize: this.pageSize,
            keyword: this.keyword
          }
        }).then(res => {
          if(res.code === '1'){
            this.total = res.data.total
            this.tableData = res.data.pageList
          }else{
            this.$message({message: res.msg, type: 'error'})
          }
        })
      },
      showArticle(id,uuid){
        this.$router.push({
          path:'/manage/articleshow',
          query:{
            id: id,
            uuid: uuid
          }
        })
      }
    }
  }
</script>