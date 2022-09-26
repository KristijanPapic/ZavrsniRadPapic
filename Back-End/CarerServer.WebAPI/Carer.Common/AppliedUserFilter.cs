using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Common
{
    public class AppliedUserFilter
    {
        private Guid adId;
        private int type;

        public Guid AdId { get => adId; set => adId = value; }
        public int Type { get => type; set => type = value; }
    }
}
