<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose :title="title" :width="1280" @ok="handleSubmit">
      <BasicForm @register="registerForm" ref="formRef" name="LttlWaybillForm" />
      <!-- 子表单区域 -->
      <a-tabs v-model:activeKey="activeKey" animated @change="handleChangeTabs">
        <a-tab-pane tab="运单货物表" key="lttlWaybillGoods" :forceRender="true">
          <JVxeTable
            keep-source
            resizable
            ref="lttlWaybillGoods"
            :loading="lttlWaybillGoodsTable.loading"
            :columns="lttlWaybillGoodsTable.columns"
            :dataSource="lttlWaybillGoodsTable.dataSource"
            :height="240"
            :disabled="formDisabled"
            :rowNumber="true"
            :rowSelection="true"
            :toolbar="true"
            />
        </a-tab-pane>
        <a-tab-pane tab="运单费用表" key="lttlWaybillFee" :forceRender="true">
          <JVxeTable
            keep-source
            resizable
            ref="lttlWaybillFee"
            :loading="lttlWaybillFeeTable.loading"
            :columns="lttlWaybillFeeTable.columns"
            :dataSource="lttlWaybillFeeTable.dataSource"
            :height="240"
            :disabled="formDisabled"
            :rowNumber="true"
            :rowSelection="true"
            :toolbar="true"
            />
        </a-tab-pane>
      </a-tabs>

  </BasicModal>
</template>

<script lang="ts" setup>
    import {ref, computed, unref,reactive} from 'vue';
    import {BasicModal, useModalInner} from '/@/components/Modal';
    import {BasicForm, useForm} from '/@/components/Form/index';
    import { JVxeTable } from '/@/components/jeecg/JVxeTable'
    import { useJvxeMethod } from '/@/hooks/system/useJvxeMethods.ts'
    import {formSchema,lttlWaybillGoodsJVxeColumns,lttlWaybillFeeJVxeColumns} from '../LttlWaybill.data';
    import {saveOrUpdate,queryLttlWaybillGoods,queryLttlWaybillFee} from '../LttlWaybill.api';
    import { VALIDATE_FAILED } from '/@/utils/common/vxeUtils'
    import { useMessage } from '/@/hooks/web/useMessage';
    import { getDateByPicker } from '/@/utils';
    //日期个性化选择
    const fieldPickers = reactive({
    });
    const lttlWaybillGoodsFieldPickers = reactive({
    });
    const lttlWaybillFeeFieldPickers = reactive({
    });
    const { createMessage } = useMessage();
    // Emits声明
    const emit = defineEmits(['register','success']);
    const isUpdate = ref(true);
    const formDisabled = ref(false);
    const refKeys = ref(['lttlWaybillGoods', 'lttlWaybillFee', ]);
    const activeKey = ref('lttlWaybillGoods');
    const lttlWaybillGoods = ref();
    const lttlWaybillFee = ref();
    const tableRefs = {lttlWaybillGoods, lttlWaybillFee, };
    const lttlWaybillGoodsTable = reactive({
          loading: false,
          dataSource: [],
          columns:lttlWaybillGoodsJVxeColumns
    })
    const lttlWaybillFeeTable = reactive({
          loading: false,
          dataSource: [],
          columns:lttlWaybillFeeJVxeColumns
    })
    //表单配置
    const [registerForm, {setProps,resetFields, setFieldsValue, validate}] = useForm({
        schemas: formSchema,
        showActionButtonGroup: false,
        // baseColProps: {span: 4},
        labelWidth: 96,
        compact: true,
    });
     //表单赋值
    const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
        //重置表单
        await reset();
        setModalProps({confirmLoading: false,showCancelBtn:data?.showFooter,showOkBtn:data?.showFooter});
        isUpdate.value = !!data?.isUpdate;
        formDisabled.value = !data?.showFooter;
        if (unref(isUpdate)) {
            //表单赋值
            await setFieldsValue({
                ...data.record,
            });
             requestSubTableData(queryLttlWaybillGoods, {id:data?.record?.id}, lttlWaybillGoodsTable)
             requestSubTableData(queryLttlWaybillFee, {id:data?.record?.id}, lttlWaybillFeeTable)
        }
        // 隐藏底部时禁用整个表单
       setProps({ disabled: !data?.showFooter })
    });
    //方法配置
    const [handleChangeTabs,handleSubmit,requestSubTableData,formRef] = useJvxeMethod(requestAddOrEdit,classifyIntoFormData,tableRefs,activeKey,refKeys);

    //设置标题
    const title = computed(() => (!unref(isUpdate) ? '新增' : !unref(formDisabled) ? '编辑' : '详情'));

    async function reset(){
      await resetFields();
      activeKey.value = 'lttlWaybillGoods';
      lttlWaybillGoodsTable.dataSource = [];
      lttlWaybillFeeTable.dataSource = [];
    }
    function classifyIntoFormData(allValues) {
         let main = Object.assign({}, allValues.formValue)
         return {
           ...main, // 展开
           lttlWaybillGoodsList: allValues.tablesValue[0].tableData,
           lttlWaybillFeeList: allValues.tablesValue[1].tableData,
         }
       }
    //表单提交事件
    async function requestAddOrEdit(values) {
        try {
            // 预处理日期数据
            changeDateValue(values);
            setModalProps({confirmLoading: true});
            //提交表单
            await saveOrUpdate(values, isUpdate.value);
            //关闭弹窗
            closeModal();
            //刷新列表
            emit('success');
        } finally {
            setModalProps({confirmLoading: false});
        }
    }

    /**
     * 处理日期值
     * @param formData 表单数据
     */
    const changeDateValue = (formData) => {
      if (formData && fieldPickers) {
          for (let key in fieldPickers) {
              if (formData[key]) {
                  formData[key] = getDateByPicker(formData[key], fieldPickers[key]);
              }
          }
      }
      if(formData && formData.lttlWaybillGoodsList && formData.lttlWaybillGoodsList.length > 0){
          formData.lttlWaybillGoodsList.forEach(subFormData=>{
              for (let key in lttlWaybillGoodsFieldPickers) {
                  if (subFormData[key]) {
                      subFormData[key] = getDateByPicker(subFormData[key], lttlWaybillGoodsFieldPickers[key]);
                  }
              }
          })
      }
      if(formData && formData.lttlWaybillFeeList && formData.lttlWaybillFeeList.length > 0){
          formData.lttlWaybillFeeList.forEach(subFormData=>{
              for (let key in lttlWaybillFeeFieldPickers) {
                  if (subFormData[key]) {
                      subFormData[key] = getDateByPicker(subFormData[key], lttlWaybillFeeFieldPickers[key]);
                  }
              }
          })
      }
    };
</script>

<style lang="less" scoped>
	/** 时间和数字输入框样式 */
  :deep(.ant-input-number) {
    width: 100%;
  }

  :deep(.ant-calendar-picker) {
    width: 100%;
  }
</style>