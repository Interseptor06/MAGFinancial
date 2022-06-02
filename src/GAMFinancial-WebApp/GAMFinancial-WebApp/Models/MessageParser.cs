using System.Numerics;

namespace GAMFinancial_WebApp.Models
{
    public class MessageParser
    {
        public static Message ParseMSG(string strMSG)
        {
            Message ResMsg = new Message(strMSG);
            string[] SplitMSG = strMSG.Split("/");
            if (SplitMSG.Length == 6 || SplitMSG.Length == 3)
            {
                throw new Exception("Number of arguments should be 6 for Add and 4 for Cancel Messages");
            }
            if (SplitMSG[0].Equals("Add"))
            {
                try
                {
                    SplitMSG[1] = SplitMSG[1].Split("=")[1];
                    SplitMSG[2] = SplitMSG[2].Split("=")[1];
                    SplitMSG[3] = SplitMSG[3].Split("=")[1];
                    SplitMSG[4] = SplitMSG[4].Split("=")[1];
                    SplitMSG[5] = SplitMSG[5].Split("=")[1];
                    SplitMSG[6] = SplitMSG[6].Split("=")[1];
                }
                catch (Exception ignored)
                {
                    throw new Exception("Wrong Format");
                }

                if (SplitMSG[1].Equals("Buy"))
                {
                    ResMsg.setoSide(OrderSide.Buy);
                }
                else if (SplitMSG[1].Equals("Sell"))
                {
                    ResMsg.setoSide(OrderSide.Sell);
                }
                else
                {
                    throw new Exception("Only Buy and Sell Operations Supported");
                }

                if (SplitMSG[2].Equals("Market"))
                {
                    ResMsg.setoClass(OrderClass.Market);
                }
                else if (SplitMSG[2].Equals("Limit"))
                {
                    ResMsg.setoClass(OrderClass.Limit);
                }
                else
                {
                    throw new Exception("Only Market and Limit Orders Supported");
                }
                ResMsg.setType(MessageType.Add);
                ResMsg.setQuantity(int.Parse(SplitMSG[3]));
                ResMsg.setPrice(decimal.Parse(SplitMSG[4]));
                ResMsg.setCustomerID(BigInteger.Parse(SplitMSG[5]));
                ResMsg.setInstrumentID(BigInteger.Parse(SplitMSG[6]));
            }
            else if (SplitMSG[0].Equals("Delete"))
            {
                try
                {
                    SplitMSG[1] = SplitMSG[1].Split("=")[1];
                    SplitMSG[2] = SplitMSG[2].Split("=")[1];
                    SplitMSG[3] = SplitMSG[3].Split("=")[1];
                }
                catch (Exception ignored)
                {
                    throw new Exception("Wrong Format");
                }
                ResMsg.setType(MessageType.Add);
                ResMsg.setOldOrderID(BigInteger.Parse(SplitMSG[1]));
                ResMsg.setInstrumentID(BigInteger.Parse(SplitMSG[2]));
                ResMsg.setCustomerID(BigInteger.Parse(SplitMSG[3]));
            }
            else
            {
                throw new Exception("Operation should be add or cancel, not " + SplitMSG[0]);
            }
            return ResMsg;
        }
    }
}
