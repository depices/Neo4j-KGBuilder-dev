<template>
  <el-dialog
    title="关系抽取"
    :visible.sync="dialogVisible"
    width="70%"
    customClass="flowHelp"
  >
    <el-alert
      style="margin:10px"
      title="关系抽取"
      type="success"
      description="关系抽取模型"
    >
    </el-alert>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="文本">
        <el-input type="textarea" v-model="form.text" rows="6" :autosize="{ minRows: 6, maxRows: 10}"></el-input>
      </el-form-item>
      <el-form-item label="主体">
          <el-input v-model="form.zhuti"></el-input>
      </el-form-item>
      <el-form-item label="关系1">
        <el-input v-model="form.gx1"></el-input>
      </el-form-item>
      <el-form-item label="关系2">
        <el-input v-model="form.gx2"></el-input>
      </el-form-item>
      <el-form-item label="关系3">
        <el-input v-model="form.gx3"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">抽取</el-button>
        <el-button @click="dialogVisible=!dialogVisible">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
  import { kgBuilderApi } from "@/api";
  export default {
    data() {
      return {
        dialogVisible: false,
        form:{
          text:"",
          zhuti:"",
          gx1:"",
          gx2:"",
          gx3:""
        }
      };
    },
    components: {},
    methods: {
      init() {
        this.dialogVisible = true;
      },
      onSubmit(){
        let data = this.form;
        kgBuilderApi.getGxcqMx(data).then(result => {
          console.log(result)
          if (result.code == 200) {
            this.dialogVisible=false;
            this.$message({
              message: "操作成功",
              type: "success"
            });
          }
        });
      }
    }
  };
</script>

<style>
  .flowHelp {
    height: 80%;
  }
</style>
