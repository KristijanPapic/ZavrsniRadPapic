using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Model
{
    public class LoginResponseModel
    {
        private bool isNewUser;
        private Guid userId;

        public bool IsNewUser { get => isNewUser; set => isNewUser = value; }
        public Guid UserId { get => userId; set => userId = value; }
    }
}
