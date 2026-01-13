import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { JVxeTypes, JVxeColumn } from '/@/components/jeecg/JVxeTable/types';
import { getWeekMonthQuarterYear } from '/@/utils';
import { list, getStationByStationId } from '../base/LttlBaseStation.api';

const stationList = (await list({})).records || [];
// const stationList = [];
// list({}).then((res) => {
//   console.log('站点列表', res);
//   stationList.push(...(res.records || []));
// });

//列表数据
export const columns: BasicColumn[] = [
  {
    title: '运单号',
    align: "center",
    dataIndex: 'billNo'
  },
  {
    title: '厂家单号',
    align: "center",
    dataIndex: 'externalBillNo'
  },
  {
    title: '厂家名称',
    align: "center",
    dataIndex: 'externalCorpName'
  },
  {
    title: '托运时间',
    align: "center",
    dataIndex: 'billTime'
  },
  {
    title: '启运站',
    align: "center",
    dataIndex: 'fromStationId_dictText'
  },
  {
    title: '经由站',
    align: "center",
    dataIndex: 'viaStationId_dictText'
  },
  {
    title: '目的站',
    align: "center",
    dataIndex: 'destStationId_dictText'
  },
  {
    title: '运输方式',
    align: "center",
    dataIndex: 'transWayId_dictText'
  },
  {
    title: '接货方式',
    align: "center",
    dataIndex: 'pickupWayId_dictText'
  },
  {
    title: '送货方式',
    align: "center",
    dataIndex: 'deliveryWayId_dictText'
  },
  {
    title: '接货车',
    align: "center",
    dataIndex: 'pickupVehiclePlateNumber'
  },
  {
    title: '接货司机',
    align: "center",
    dataIndex: 'pickupDriverName'
  },
  {
    title: '发货人',
    align: "center",
    dataIndex: 'consignorName'
  },
  {
    title: '发货地区',
    align: "center",
    dataIndex: 'consignorAreaCode',
  },
  {
    title: '发货地址',
    align: "center",
    dataIndex: 'consignorAddress'
  },
  {
    title: '收货人',
    align: "center",
    dataIndex: 'consigneeName'
  },
  {
    title: '收货地区',
    align: "center",
    dataIndex: 'consigneeAreaCode',
  },
  {
    title: '收货地址',
    align: "center",
    dataIndex: 'consigneeAddress'
  },
  {
    title: '付款方式',
    align: "center",
    dataIndex: 'paymentTypeId_dictText'
  },
  {
    title: '回单要求',
    align: "center",
    dataIndex: 'receiptTypeId_dictText'
  },
  {
    title: '时效（天）',
    align: "center",
    dataIndex: 'timeLimit'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'remark'
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
const colPropsSpan = 6;
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '运单号',
    field: 'billNo',
    component: 'Input',
    dynamicDisabled: true,
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '厂家单号',
    field: 'externalBillNo',
    component: 'Input',
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '厂家名称',
    field: 'externalCorpName',
    component: 'Input',
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '托运时间',
    field: 'billTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss'
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '启运站',
    field: 'fromStationId',
    component: 'JSearchSelect',
    componentProps: ({ schema, tableAction, formActionType, formModel }) => {
      return {
        dict: "lttl_base_station,station_name,id",
        onChange: (e: any) => {
          console.log('启运站选择值', e);
          formModel.viaStationId = undefined;
          formModel.destStationId = undefined;
          console.log('stationList', stationList);
          stationList.forEach((item) => {
            if (item.id === e) {
              formModel.viaStationId = item.defaultTransferStation;
              formModel.destStationId = item.defaultDestStation;
              return;
            }
          });
          // getStationByStationId({ id: e }).then((res) => {
          //   console.log('起运站信息', res);
          //   formModel.viaStationId = res.defaultTransferStation;
          //   formModel.destStationId = res.defaultDestStation;
          //   // const { updateSchema } = formActionType;
          //   // updateSchema({
          //   //   field: 'viaStationId',
          //   //   componentProps: {
          //   //     options: citiesOptions,
          //   //   },
          //   // });
          // });
          // // console.log(e)
          // let citiesOptions = e == 1 ? citiesOptionsData[provincesOptions[0].id] : citiesOptionsData[provincesOptions[1].id];
          // // console.log(citiesOptions)
          // if (e === undefined) {
          //   citiesOptions = [];
          // }
          // formModel.city = undefined; //  reset city value
          // const { updateSchema } = formActionType;
          // updateSchema({
          //   field: 'city',
          //   componentProps: {
          //     options: citiesOptions,
          //   },
          // });
        },
      };
    },
    dynamicRules: ({ model, schema }) => {
      return [
        { required: true, message: '请输入启运站!' },
      ];
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '经由站',
    field: 'viaStationId',
    component: 'JSearchSelect',
    componentProps: {
      dict: "lttl_base_station,station_name,id"
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '目的站',
    field: 'destStationId',
    component: 'JSearchSelect',
    componentProps: {
      dict: "lttl_base_station,station_name,id"
    },
    dynamicRules: ({ model, schema }) => {
      return [
        { required: true, message: '请输入目的站!' },
      ];
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '运输方式',
    field: 'transWayId',
    defaultValue: "1",
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: "lttl_trans_way"
    },
    dynamicRules: ({ model, schema }) => {
      return [
        { required: true, message: '请输入运输方式!' },
      ];
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '接货方式',
    field: 'pickupWayId',
    defaultValue: "1",
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: "lttl_pickup_way"
    },
    dynamicRules: ({ model, schema }) => {
      return [
        { required: true, message: '请输入接货方式!' },
      ];
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '送货方式',
    field: 'deliveryWayId',
    defaultValue: "1",
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: "lttl_delivery_way"
    },
    dynamicRules: ({ model, schema }) => {
      return [
        { required: true, message: '请输入送货方式!' },
      ];
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '接货车',
    field: 'pickupVehiclePlateNumber',
    component: 'JPopup',
    componentProps: ({ formActionType }) => {
      const { setFieldsValue } = formActionType;
      return {
        setFieldsValue: setFieldsValue,
        code: "vehicle_info",
        fieldConfig: [
          { source: 'vehicle_id', target: 'pickupVehicleId' },
          { source: 'plate_number', target: 'pickupVehiclePlateNumber' },
          { source: 'driver_name', target: 'pickupDriverName' },
        ],
        multi: true
      }
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '接货司机',
    field: 'pickupDriverName',
    component: 'Input',
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '发货人',
    field: 'consignorName',
    component: 'JPopup',
    componentProps: ({ formActionType }) => {
      const { setFieldsValue } = formActionType;
      return {
        setFieldsValue: setFieldsValue,
        code: "cust_contact_consignor",
        fieldConfig: [
          { source: 'consignor_id', target: 'consignorId' },
          { source: 'consignor_name', target: 'consignorName' },
          { source: 'consignor_area_code', target: 'consignorAreaCode' },
          { source: 'consignor_address', target: 'consignorAddress' },
          { source: 'consignee_id', target: 'consigneeId' },
          { source: 'consignee_name', target: 'consigneeName' },
          { source: 'consignee_area_code', target: 'consigneeAreaCode' },
          { source: 'consignee_address', target: 'consigneeAddress' },
        ],
        multi: true
      }
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '发货地区',
    field: 'consignorAreaCode',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '发货地址',
    field: 'consignorAddress',
    component: 'Input',
    colProps: {
      span: colPropsSpan*2,
    },
  },
  {
    label: '收货人',
    field: 'consigneeName',
    component: 'JPopup',
    componentProps: ({ formActionType }) => {
      const { setFieldsValue } = formActionType;
      return {
        setFieldsValue: setFieldsValue,
        code: "cust_contact_consignee",
        fieldConfig: [
          { source: 'consignee_id', target: 'consigneeId' },
          { source: 'consignee_name', target: 'consigneeName' },
          { source: 'consignee_area_code', target: 'consigneeAreaCode' },
          { source: 'consignee_address', target: 'consigneeAddress' },
        ],
        multi: true
      }
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '收货地区',
    field: 'consigneeAreaCode',
    component: 'JAreaLinkage',
    componentProps: {
      saveCode: 'region',
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '收货地址',
    field: 'consigneeAddress',
    component: 'Input',
    colProps: {
      span: colPropsSpan*2,
    },
  },
  {
    label: '付款方式',
    field: 'paymentTypeId',
    defaultValue: "1",
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: "lttl_payment_way"
    },
    dynamicRules: ({ model, schema }) => {
      return [
        { required: true, message: '请输入付款方式!' },
      ];
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '回单要求',
    field: 'receiptTypeId',
    defaultValue: "1",
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: "lttl_receipt_type"
    },
    colProps: {
      span: colPropsSpan,
    },
  },
  {
    label: '时效（天）',
    field: 'timeLimit',
    component: 'InputNumber',
    colProps: {
      span: colPropsSpan,
    },
  },
  // {
  //   component: 'Divider',
  //   label: '',
  //   componentProps: {
  //     dashed: true,
  //     orientation: 'left',
  //     orientationMargin: 0,
  //   },
  // },
  {
    label: '备注',
    field: 'remark',
    component: 'InputTextArea',
    colProps: {
      span: colPropsSpan*3,
    },
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false
  },
];
//子表单数据
//子表列表数据
export const lttlWaybillGoodsColumns: BasicColumn[] = [
  {
    title: '货物代码',
    align: "center",
    dataIndex: 'goodsNo'
  },
  {
    title: '品名',
    align: "center",
    dataIndex: 'goodsName'
  },
  {
    title: '包装',
    align: "center",
    dataIndex: 'goodsPkg_dictText'
  },
  {
    title: '件数',
    align: "center",
    dataIndex: 'goodsPcs'
  },
  {
    title: '元/件',
    align: "center",
    dataIndex: 'unitPrice'
  },
  {
    title: '重量(千克)',
    align: "center",
    dataIndex: 'goodsWeight'
  },
  {
    title: '元/kg',
    align: "center",
    dataIndex: 'unitPriceWeight'
  },
  {
    title: '体积(方)',
    align: "center",
    dataIndex: 'goodsVolume'
  },
  {
    title: '元/方',
    align: "center",
    dataIndex: 'unitPriceVolume'
  },
  {
    title: '规格',
    align: "center",
    dataIndex: 'goodsSpecs'
  },
  {
    title: '运费',
    align: "center",
    dataIndex: 'shippingFee'
  },
  {
    title: '运费折扣',
    align: "center",
    dataIndex: 'shippingFeeDiscount'
  },
  {
    title: '信息费',
    align: "center",
    dataIndex: 'infoFee'
  },
  {
    title: '返佣方式',
    align: "center",
    dataIndex: 'infoFeePaymentType'
  },
  {
    title: '代收货款',
    align: "center",
    dataIndex: 'codAmount'
  },
  {
    title: '送货费',
    align: "center",
    dataIndex: 'deliveryFee'
  },
  {
    title: '保险费',
    align: "center",
    dataIndex: 'insuranceFee'
  },
  {
    title: '接货费',
    align: "center",
    dataIndex: 'pickupFee'
  },
  {
    title: '实际接货费',
    align: "center",
    dataIndex: 'actualPickupFee'
  },
  {
    title: '装卸费',
    align: "center",
    dataIndex: 'luFee'
  },
  {
    title: '其他费用',
    align: "center",
    dataIndex: 'otherFee'
  },
  {
    title: '仓位',
    align: "center",
    dataIndex: 'storingLocation'
  },
  {
    title: '进仓费',
    align: "center",
    dataIndex: 'storingFee'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'remark'
  },
];
//子表列表数据
export const lttlWaybillFeeColumns: BasicColumn[] = [
  {
    title: '费用编号',
    align: "center",
    dataIndex: 'feeNo'
  },
  {
    title: '费用类型',
    align: "center",
    dataIndex: 'feeTypeId_dictText'
  },
  {
    title: '费用金额',
    align: "center",
    dataIndex: 'feeAmount'
  },
  {
    title: '收支类型',
    align: "center",
    dataIndex: 'ieType_dictText'
  },
  {
    title: '备注',
    align: "center",
    dataIndex: 'remark'
  },
];
//子表表格配置
export const lttlWaybillGoodsJVxeColumns: JVxeColumn[] = [
  {
    title: '货物代码',
    key: 'goodsNo',
    type: JVxeTypes.input,
    width: "160px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '品名',
    key: 'goodsName',
    type: JVxeTypes.input,
    width: "160px",
    placeholder: '请输入${title}',
    defaultValue: '',
    validateRules: [
      { required: true, message: '${title}不能为空' },
      { pattern: "*", message: "${title}格式不正确" }
    ],
  },
  {
    title: '包装',
    key: 'goodsPkg',
    type: JVxeTypes.select,
    options: [],
    dictCode: "lttl_package_type",
    width: "100px",
    placeholder: '请选择${title}',
    defaultValue: "1",
    validateRules: [
      { required: true, message: '${title}不能为空' },
      { pattern: "*", message: "${title}格式不正确" }
    ],
  },
  {
    title: '件数',
    key: 'goodsPcs',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
    validateRules: [
      { required: true, message: '${title}不能为空' },
      { pattern: "*", message: "${title}格式不正确" }
    ],
  },
  {
    title: '元/件',
    key: 'unitPrice',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '重量(kg)',
    key: 'goodsWeight',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '元/kg',
    key: 'unitPriceWeight',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '体积(方)',
    key: 'goodsVolume',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '元/方',
    key: 'unitPriceVolume',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '规格',
    key: 'goodsSpecs',
    type: JVxeTypes.input,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '运费',
    key: 'shippingFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '运费折扣',
    key: 'shippingFeeDiscount',
    type: JVxeTypes.inputNumber,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '信息费',
    key: 'infoFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '返佣方式',
    key: 'infoFeePaymentType',
    type: JVxeTypes.input,
    width: "100px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '代收货款',
    key: 'codAmount',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '送货费',
    key: 'deliveryFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '保险费',
    key: 'insuranceFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '接货费',
    key: 'pickupFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '实际接货费',
    key: 'actualPickupFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '装卸费',
    key: 'luFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '其他费用',
    key: 'otherFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '仓位',
    key: 'storingLocation',
    type: JVxeTypes.input,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '进仓费',
    key: 'storingFee',
    type: JVxeTypes.inputNumber,
    width: "120px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '备注',
    key: 'remark',
    type: JVxeTypes.textarea,
    width: "200px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
]
export const lttlWaybillFeeJVxeColumns: JVxeColumn[] = [
  {
    title: '费用编号',
    key: 'feeNo',
    type: JVxeTypes.input,
    width: "200px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '费用类型',
    key: 'feeTypeId',
    type: JVxeTypes.select,
    options: [],
    dictCode: "lttl_fee_type",
    width: "200px",
    placeholder: '请输入${title}',
    defaultValue: "1",
  },
  {
    title: '费用金额',
    key: 'feeAmount',
    type: JVxeTypes.inputNumber,
    width: "200px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
  {
    title: '收支类型',
    key: 'ieType',
    type: JVxeTypes.select,
    options: [],
    dictCode: "lttl_transaction_type",
    width: "200px",
    placeholder: '请输入${title}',
    defaultValue: "1",
  },
  {
    title: '备注',
    key: 'remark',
    type: JVxeTypes.textarea,
    width: "200px",
    placeholder: '请输入${title}',
    defaultValue: '',
  },
]

// 高级查询数据
export const superQuerySchema = {
  billNo: { title: '运单号', order: 0, view: 'text', type: 'string', },
  externalBillNo: { title: '厂家单号', order: 1, view: 'text', type: 'string', },
  externalCorpName: { title: '厂家名称', order: 2, view: 'text', type: 'string', },
  billTime: { title: '托运时间', order: 3, view: 'datetime', type: 'string', },
  fromStationId: { title: '启运站', order: 4, view: 'sel_search', type: 'string', dictTable: "lttl_base_station", dictCode: 'id', dictText: 'station_name', },
  viaStationId: { title: '经由站', order: 5, view: 'sel_search', type: 'string', dictTable: "lttl_base_station", dictCode: 'id', dictText: 'station_name', },
  destStationId: { title: '目的站', order: 6, view: 'sel_search', type: 'string', dictTable: "lttl_base_station", dictCode: 'id', dictText: 'station_name', },
  transWayId: { title: '运输方式', order: 7, view: 'list', type: 'string', dictCode: 'lttl_trans_way', },
  pickupWayId: { title: '接货方式', order: 8, view: 'list', type: 'string', dictCode: 'lttl_pickup_way', },
  deliveryWayId: { title: '送货方式', order: 9, view: 'list', type: 'string', dictCode: 'lttl_delivery_way', },
  pickupVehiclePlateNumber: { title: '接货车', order: 10, view: 'popup', type: 'string', code: 'vehicle_info', orgFields: 'plate_number', destFields: 'pickupVehiclePlateNumber', popupMulti: false, },
  pickupDriverName: { title: '接货司机', order: 11, view: 'text', type: 'string', },
  consignorName: { title: '发货人', order: 12, view: 'popup', type: 'string', code: 'cust_contact_consignor', orgFields: 'consignor_name', destFields: 'consignorName', popupMulti: false, },
  consignorAreaCode: { title: '发货地区', order: 13, view: 'pca', type: 'string', },
  consignorAddress: { title: '发货地址', order: 14, view: 'text', type: 'string', },
  consigneeName: { title: '收货人', order: 15, view: 'popup', type: 'string', code: 'cust_contact_consignee', orgFields: 'consignee_name', destFields: 'consigneeName', popupMulti: false, },
  consigneeAreaCode: { title: '收货地区', order: 16, view: 'pca', type: 'string', },
  consigneeAddress: { title: '收货地址', order: 17, view: 'text', type: 'string', },
  paymentTypeId: { title: '付款方式', order: 18, view: 'list', type: 'string', dictCode: 'lttl_payment_way', },
  receiptTypeId: { title: '回单要求', order: 19, view: 'list', type: 'string', dictCode: 'lttl_receipt_type', },
  timeLimit: { title: '时效（天）', order: 20, view: 'number', type: 'number', },
  remark: { title: '备注', order: 21, view: 'text', type: 'string', },
  //子表高级查询
  lttlWaybillGoods: {
    title: '运单货物表',
    view: 'table',
    fields: {
      goodsNo: { title: '货物代码', order: 0, view: 'text', type: 'string', },
      goodsName: { title: '品名', order: 1, view: 'text', type: 'string', },
      goodsPkg: { title: '包装', order: 2, view: 'list', type: 'string', dictCode: 'lttl_package_type', },
      goodsPcs: { title: '件数', order: 3, view: 'number', type: 'number', },
      unitPrice: { title: '元/件', order: 4, view: 'number', type: 'number', },
      goodsWeight: { title: '重量(千克)', order: 5, view: 'number', type: 'number', },
      unitPriceWeight: { title: '元/kg', order: 6, view: 'number', type: 'number', },
      goodsVolume: { title: '体积(方)', order: 7, view: 'number', type: 'number', },
      unitPriceVolume: { title: '元/方', order: 8, view: 'number', type: 'number', },
      goodsSpecs: { title: '规格', order: 9, view: 'text', type: 'string', },
      shippingFee: { title: '运费', order: 10, view: 'number', type: 'number', },
      shippingFeeDiscount: { title: '运费折扣', order: 11, view: 'number', type: 'number', },
      infoFee: { title: '信息费', order: 12, view: 'number', type: 'number', },
      infoFeePaymentType: { title: '返佣方式', order: 13, view: 'text', type: 'string', },
      codAmount: { title: '代收货款', order: 14, view: 'number', type: 'number', },
      deliveryFee: { title: '送货费', order: 15, view: 'number', type: 'number', },
      insuranceFee: { title: '保险费', order: 16, view: 'number', type: 'number', },
      pickupFee: { title: '接货费', order: 17, view: 'number', type: 'number', },
      actualPickupFee: { title: '实际接货费', order: 18, view: 'number', type: 'number', },
      luFee: { title: '装卸费', order: 19, view: 'number', type: 'number', },
      otherFee: { title: '其他费用', order: 20, view: 'number', type: 'number', },
      storingLocation: { title: '仓位', order: 21, view: 'text', type: 'string', },
      storingFee: { title: '进仓费', order: 22, view: 'number', type: 'number', },
      remark: { title: '备注', order: 23, view: 'textarea', type: 'string', },
    }
  },
  lttlWaybillFee: {
    title: '运单费用表',
    view: 'table',
    fields: {
      feeNo: { title: '费用编号', order: 0, view: 'text', type: 'string', },
      feeTypeId: { title: '费用类型', order: 1, view: 'list', type: 'string', dictCode: 'lttl_fee_type', },
      feeAmount: { title: '费用金额', order: 2, view: 'number', type: 'number', },
      ieType: { title: '收支类型', order: 3, view: 'list', type: 'string', dictCode: 'lttl_transaction_type', },
      remark: { title: '备注', order: 4, view: 'textarea', type: 'string', },
    }
  },
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}