<!--部门选择框-->
<template>
  <div v-if="true">
    <BasicModal v-bind="$attrs" @register="register" :title="modalTitle" :width="width" :maxHeight="maxHeight" @ok="handleOk" @cancel="handleCancel" destroyOnClose>
      <!-- 显示加载中的提示 -->
      <div v-if="loading" class="loading-container">
        <a-spin size="large" tip="页面加载中..."></a-spin>
      </div>
      
      <!-- 使用iframe加载指定页面 -->
      <iframe
        v-if="pageUrl"
        :src="pageUrl"
        class="modal-iframe"
        frameborder="0"
        @load="handleIframeLoad"
      ></iframe>
      <template>
          <a-button style="float: left" @click="navigateToAddPageInModal"> 跳转页面 </a-button>
      </template>
    </BasicModal>
  </div>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { router } from "/@/router";
  import { useAttrs } from '/@/hooks/core/useAttrs';
  import { propTypes } from "/@/utils/propTypes";

  export default defineComponent({
    name: 'TestDynamicImportModal',
    components: {
      BasicModal,
    },
    props: {
      // 要加载的页面URL
      pageUrl: {
        type: String,
        default: '',
        description: '要在模态框内加载的页面URL'
      },
      //选择框标题
      modalTitle: {
        type: String,
        default: '添加页面1',
      },
      // 模态框宽度
      width: {
        type: [Number, String],
        default: 800,
      },
      // update-begin--author:liaozhiyang---date:20231220---for：【QQYUN-7678】部门组件内容过多没有滚动条（给一个默认最大高）
      maxHeight: {
        type: Number,
        default: 500,
      },
      // update-end--author:liaozhiyang---date:20231220---for：【QQYUN-7678】部门组件内容过多没有滚动条（给一个默认最大高）
      value: propTypes.oneOfType([propTypes.string, propTypes.array]),
    },
    emits: ['register', 'close', 'success'],
    setup(props, { emit }) {
      console.log('setup', props);
      const attrs = useAttrs();
      const loading = ref(true); // 页面加载状态

      // 使用useModalInner而不是useModal
      const [register, { closeModal }] = useModalInner(() => {
        console.log('模态框打开了');
      });

      // 处理iframe加载完成事件
      function handleIframeLoad() {
        loading.value = false;
        emit('load'); // 触发加载完成事件
      }

      function navigateToAddPageInModal() {
        console.log('navigateToAddPageInModal');
        const url='/lttl/base/station';
        console.log('navigateToAddPage', url);
        router.push(url);
        // 关闭模态框
        closeModal();
        // 触发success事件
        emit('success');
      }
      
      // 跳转到指定页面（新窗口或当前窗口）
      function navigateToPage() {
        if (props.pageUrl) {
          // 可以根据需要选择是在当前窗口跳转还是新窗口打开
          // router.push(props.pageUrl); // 当前窗口跳转
          window.open(props.pageUrl, '_blank'); // 新窗口打开
          
          // 关闭模态框
          closeModal();
          emit('success');
        }
      }

      function handleOk() {
        console.log('handleOk');
        closeModal();
        emit('success');
      }

      // 处理取消按钮点击事件
      function handleCancel() {
        closeModal();
        emit('close');
      }

      return {
        navigateToPage,
        navigateToAddPageInModal,
        handleOk,
        handleCancel,
        handleIframeLoad,
        register,
        loading,
      };
    },
  });
</script>
<style lang="less" scoped>
  .loading-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 400px;
  }
  
  .modal-iframe {
    width: 100%;
    height: calc(100vh - 300px);
    min-height: 400px;
    border: none;
  }
  
  .jump-button-container {
    margin-top: 16px;
    display: flex;
    justify-content: center;
  }
</style>
